package com.xl.repository.impl;

import com.xl.entity.THngyTeacherInfo;
import com.xl.repository.TeacherRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeacherRepositoryImpl implements TeacherRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public THngyTeacherInfo load(Long id) {
        return getSession().get(THngyTeacherInfo.class, id);
    }

    @Override
    public THngyTeacherInfo get(Long id) {
        return getSession().get(THngyTeacherInfo.class, id);
    }

    @Override
    public List<THngyTeacherInfo> findAll() {
        String hql = "from THngyTeacherInfo ";
        return getSession().createQuery(hql).list();
    }

    @Override
    public void persist(THngyTeacherInfo entity) {
    }

    @Override
    public Long save(THngyTeacherInfo entity) {
        return (Long) getSession().save(entity);
    }

    @Override
    public void saveOrUpdate(THngyTeacherInfo entity) {
        saveOrUpdate(entity);
    }

    @Override
    public void delete(Long id) {
        getSession().delete(getSession().get(THngyTeacherInfo.class, id));
    }

    @Override
    public void flush() {

    }

}
