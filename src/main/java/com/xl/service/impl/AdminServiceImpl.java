package com.xl.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.xl.entity.*;
import com.xl.repository.AdminRepository;
import com.xl.repository.MainRepository;
import com.xl.service.AdminService;
import com.xl.utils.Config;
import com.xl.utils.ExcelUtil;
import com.xl.utils.MainUtil;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MainRepository mainRepository;

    /**
     * 以Json形式返回管理员个人信息以及公告
     *
     * @param id HttpSession中的id
     * @return 返回json格式的数据
     */
    @Override
    public String getAdminHomePageInfo(String id) {
        String json = Config.Code101;
        THngyAdminInfo admin = adminRepository.get(Long.parseLong(id));
        String hql = "from THngyNotice";
        List<Object> list = mainRepository.simpleQuery(null, hql);
        THngyNotice notice = (THngyNotice) list.get(list.size() - 1);
        if (admin != null && notice != null) {
            Map<String, Object> map = new HashMap();
            map.put("adminName", admin.getAdminInfoName());
            map.put("adminId", admin.getAdminInfoId());
            map.put("adminEmail", admin.getAdminInfoEmail());
            map.put("adminQQ", admin.getAdminInfoQq());
            map.put("notice_text", notice.getNoticeText());
            json = JSONArray.toJSONString(map);
        }
        return json;
    }

    /**
     * 以Json形式返回管理员主页最近任务信息
     *
     * @return 返回json格式的数据
     */
    @Override
    public String getAdminHomePageInfo() {
        String json = Config.Code101;
        //获取当前时间和前一个月的时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        java.sql.Date date2 = java.sql.Date.valueOf(format.format(c.getTime()));
        c.add(Calendar.MONTH, -1);
        java.sql.Date date1 = java.sql.Date.valueOf(format.format(c.getTime()));

        String hql = "select work.workTaskId,work.workTaskTime,work.workTaskName,teacher.teacherName,work.workTaskSchedule,teacher.teacherId,work.qq,work.workTaskText from THngyWorkTask as work ,THngyLink as link,THngyTeacherInfo as teacher where link.workTaskId = work.workTaskId and link.teacherId = teacher.teacherId and work.workTaskTime>=? and work.workTaskTime<=? order by work.workTaskTime desc";
        List<Map<String, Object>> list = MainUtil.getWorkInfoUtil(mainRepository.dateQuery(date1, date2, hql));
        if (list.size() > 0) {
            json = JSONArray.toJSONString(list);
        }
        return json;
    }

    /**
     * 修改管理员信息
     *
     * @param id    管理员基本信息
     * @param email
     * @param qq
     * @param pwd
     * @return 返回CONFIG类中的状态码
     */
    @Override
    public String updateAdminInfo(String id, String email, String qq, String pwd) {
        THngyAdminInfo admin = adminRepository.get(Long.parseLong(id));
        admin.setAdminInfoEmail(email);
        admin.setAdminInfoQq(qq);
        if (pwd.length() >= 6)
            admin.setAdminInfoPassWord(pwd);
        adminRepository.saveOrUpdate(admin);
        return Config.Code200;
    }

    /***
     * 修改公告
     * @param notice 公告内容
     * @return 返回状态码200成功，201失败
     */
    @Override
    public String updateNotice(String notice, long did) {
        String code = Config.Code201;
        THngyNotice notice1 = new THngyNotice();
        notice1.setDepartmentId(did);
        notice1.setNoticeText(notice);
        if (mainRepository.save(notice1) > 0) {
            code = Config.Code200;
        }
        return code;
    }

    /***
     * 根据时间获取教师工作状态(查询时间内完成任务数)
     * @return 报表
     */
    @Override
    public List<Map<String, Object>> teacherReportsQuery(java.sql.Date date1, java.sql.Date date2) {
        List<Map<String, Object>> list = new ArrayList<>();

        //查没有任务的
        String hql = "select teacher.teacherId,teacher.teacherName from THngyTeacherInfo as teacher where teacher.teacherId not in (select link.teacherId from THngyLink as link,THngyWorkTask as work where link.workTaskId=work.workTaskId and work.workTaskTime>=? and work.workTaskTime<=?)";
        List<Object[]> listTeacher = mainRepository.dateQuery(date1, date2, hql);
        for (int i = 0; i < listTeacher.size(); ++i) {
            Object[] object1 = listTeacher.get(i);
            Map<String, Object> map = new HashMap<>();
            map.put("teacherId", String.valueOf(object1[0]));
            map.put("teacherName", String.valueOf(object1[1]));
            map.put("taskCount", 0);
            map.put("unfinished", 0);
            list.add(map);
        }
        //查有任务的
        hql = "select teacher.teacherId,teacher.teacherName,count (*) ,( select count(*) from THngyWorkTask as w,THngyLink as l where l.teacherId = teacher.teacherId and l.workTaskId = w.workTaskId and w.workTaskSchedule = '未完成') from THngyTeacherInfo as teacher,THngyLink as link,THngyWorkTask as work where teacher.teacherId = link.teacherId and  link.workTaskId = work.workTaskId and work.workTaskTime>=? and work.workTaskTime<=? group by teacher.teacherId,teacher.teacherName order by count (work.workTaskId) asc ";
        List<Object[]> listWork = mainRepository.dateQuery(date1, date2, hql);
        for (int i = 0; i < listWork.size(); ++i) {
            Object[] object1 = listWork.get(i);
            Map<String, Object> map = new HashMap<>();
            map.put("teacherId", String.valueOf(object1[0]));
            map.put("teacherName", String.valueOf(object1[1]));
            map.put("taskCount", object1[2]);
            map.put("unfinished", object1[3]);
            list.add(map);
        }
        return list;
    }

    /***
     * Excel批量注册 教师信息
     *
     * @param filePath
     * @return
     */
    @Override
    public String importExcelInfo(String filePath) {
        String statusCode = Config.Code201;
        List<THngyTeacherInfo> list = ExcelUtil.getTeacherInfo(filePath);
        for (int i = 0; i < list.size(); i++) {
            if (mainRepository.save(list.get(i)) > 0) {
                statusCode = Config.Code200;
            }
        }
        return statusCode;
    }

    /***
     * 发布任务
     * @param workName 任务标题
     * @param teacher 管理员指定的多个教师,以","隔开
     * @param workText 任务详情
     * @param qq 发布任务的管理员的qq
     * @return 任务的id 前台跳转到相应任务页面
     *
     */
    @Override
    public String saveTaskTeacherLinkInfo(String workName, String teacher, String workText, String qq) {
        //获取当前时间,保存任务信息
        String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        int M = Integer.parseInt((String) time.subSequence(5, 7));
        THngyWorkTask tHngyWorkTask = new THngyWorkTask();
        tHngyWorkTask.setWorkTaskTime(java.sql.Timestamp.valueOf(time));
        tHngyWorkTask.setWorkTaskName(workName);
        tHngyWorkTask.setWorkTaskText(workText);
        tHngyWorkTask.setWorkTaskSchedule("未完成");
        tHngyWorkTask.setQq(qq);
        if (M < 2 && M > 8)//上学期
        {
            tHngyWorkTask.setWorkTaskTerm("上学期");
        } else//下学期
        {
            tHngyWorkTask.setWorkTaskTerm("下学期");
        }
        long workId = mainRepository.save(tHngyWorkTask);

        //获取全部教师的id,将教师id与对应的任务id存如Link表
        String[] teachers = teacher.split(",");
        for (int i = 0; i < teachers.length; i++) {
            String hql = "select t.id from THngyTeacherInfo t where teacherName = ?";
            Object[] objects = {teachers[i]};
            THngyLink link = new THngyLink();
            link.setWorkTaskId(workId);
            link.setTeacherId((long) mainRepository.singleQuery(objects, hql));
            mainRepository.save(link);
        }
        return String.valueOf(workId);
    }

    /**
     * 根据id获取任务数据
     *
     * @return json数据
     */
    @Override
    public String getTaskInfoForAdmin(long id) {
        String hql = "select work.workTaskId,work.workTaskTime,work.workTaskName,teacher.teacherName,work.workTaskSchedule,teacher.teacherId,work.qq,work.workTaskText from THngyWorkTask as work ,THngyLink as link,THngyTeacherInfo as teacher where link.workTaskId = work.workTaskId and link.teacherId = teacher.teacherId and work.workTaskId = ? order by work.workTaskTime desc";
        Object[] objects = {id};
        List<Object[]> listWork = mainRepository.complexQuery(objects, hql);
        String json = JSONArray.toJSONString(MainUtil.getWorkInfoUtil(listWork));
        return json;
    }

    /**
     * 编辑保存任务，更新任务的数据
     *
     * @param workTask
     * @return 状态码
     */
    @Override
    public String updateTask(THngyWorkTask workTask) {
        mainRepository.update(workTask);
        return "100";
    }

    /**
     * 根据时间查询工作报表
     *
     * @param date1
     * @param date2
     * @return 表单集合
     */
    @Override
    public List<Map<String, Object>> taskReportsQuery(java.sql.Date date1, java.sql.Date date2) {
        String hql = "select work.workTaskId,work.workTaskTime,work.workTaskName,teacher.teacherName,work.workTaskSchedule,teacher.teacherId,work.qq,work.workTaskText from THngyWorkTask as work ,THngyLink as link,THngyTeacherInfo as teacher where link.workTaskId = work.workTaskId and link.teacherId = teacher.teacherId and work.workTaskTime>=? and work.workTaskTime<=? order by work.workTaskTime desc";
        List<Map<String, Object>> list = MainUtil.getWorkInfoUtil(mainRepository.dateQuery(date1, date2, hql));
        return list;
    }

    /**
     * 根据时间查询工作报表
     *
     * @param response
     * @param list
     * @param fileName
     * @param columnNames
     * @param keys
     */
    @Override
    public void downloadReports(HttpServletResponse response, List<Map<String, Object>> list, String fileName, String[] columnNames, String[] keys) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            //调用工具类创建excel工作簿
            ExcelUtil.createWorkbook(list, keys, columnNames)
                    .write(os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);

            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String((fileName + ".xlsx").getBytes(), "iso-8859-1"));
            OutputStream out = response.getOutputStream();
            //下载传输
            byte[] b = new byte[2048];
            int length;
            while ((length = is.read(b)) > 0) {
                out.write(b, 0, length);
            }

            // 关闭
            os.flush();
            os.close();
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

