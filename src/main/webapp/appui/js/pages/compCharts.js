/*
 *  Document   : compCharts.js
 *  Author     : pixelcave
 */
var CompCharts=function(){var o=function(o,i){return Math.floor(Math.random()*(i-o+1))+o};return{init:function(){var i={type:"line",width:"120px",height:"65px",tooltipOffsetX:-25,tooltipOffsetY:20,lineColor:"#de815c",fillColor:"#de815c",spotColor:"#555555",minSpotColor:"#555555",maxSpotColor:"#555555",highlightSpotColor:"#555555",highlightLineColor:"#555555",spotRadius:3,tooltipPrefix:"",tooltipSuffix:" Tickets",tooltipFormat:"{{prefix}}{{y}}{{suffix}}"};$("#mini-chart-line1").sparkline("html",i),i.lineColor="#5ccdde",i.fillColor="#5ccdde",i.tooltipPrefix="$ ",i.tooltipSuffix="",$("#mini-chart-line2").sparkline("html",i);var t={type:"bar",barWidth:7,barSpacing:6,height:"65px",tooltipOffsetX:-25,tooltipOffsetY:20,barColor:"#de815c",tooltipPrefix:"",tooltipSuffix:" Tickets",tooltipFormat:"{{prefix}}{{value}}{{suffix}}"};$("#mini-chart-bar1").sparkline("html",t),t.barColor="#5ccdde",t.tooltipPrefix="$ ",t.tooltipSuffix="",$("#mini-chart-bar2").sparkline("html",t);var l;$(".toggle-pies").click(function(){$(".pie-chart").each(function(){l=o(1,100),$(this).data("easyPieChart").update(l)})});var a=$("#chart-classic"),e=$("#chart-stacked"),r=$("#chart-pie"),s=$("#chart-bars"),c=[[1,1900],[2,2300],[3,3200],[4,2500],[5,4200],[6,3100],[7,3600],[8,2500],[9,4600],[10,3700],[11,4200],[12,5200]],n=[[1,850],[2,750],[3,1500],[4,900],[5,1500],[6,1150],[7,1500],[8,900],[9,1800],[10,1700],[11,1900],[12,2550]],p=[[1,130],[2,330],[3,220],[4,350],[5,150],[6,275],[7,280],[8,380],[9,120],[10,330],[11,190],[12,410]],d=[[1,200],[4,350],[7,700],[10,950],[13,800],[16,1050],[19,1200],[22,750],[25,980],[28,1300],[31,1350],[34,1200]],f=[[2,450],[5,700],[8,980],[11,1200],[14,1350],[17,1200],[20,1530],[23,1750],[26,1300],[29,1620],[32,1750],[35,1750]],h=[[1,"Jan"],[2,"Feb"],[3,"Mar"],[4,"Apr"],[5,"May"],[6,"Jun"],[7,"Jul"],[8,"Aug"],[9,"Sep"],[10,"Oct"],[11,"Nov"],[12,"Dec"]],u=[[2,"Jan"],[5,"Feb"],[8,"Mar"],[11,"Apr"],[14,"May"],[17,"Jun"],[20,"Jul"],[23,"Aug"],[26,"Sep"],[29,"Oct"],[32,"Nov"],[35,"Dec"]];$.plot(a,[{label:"Earnings",data:c,lines:{show:!0,fill:!0,fillColor:{colors:[{opacity:.6},{opacity:.6}]}},points:{show:!0,radius:5}},{label:"Sales",data:n,lines:{show:!0,fill:!0,fillColor:{colors:[{opacity:.2},{opacity:.2}]}},points:{show:!0,radius:5}},{label:"Tickets",data:p,lines:{show:!0,fill:!0,fillColor:{colors:[{opacity:.2},{opacity:.2}]}},points:{show:!0,radius:5}}],{colors:["#5ccdde","#454e59","#ffffff"],legend:{show:!0,position:"nw",backgroundOpacity:0},grid:{borderWidth:0,hoverable:!0,clickable:!0},yaxis:{tickColor:"#f5f5f5",ticks:3},xaxis:{ticks:h,tickColor:"#f5f5f5"}});var b=null,g=null;a.bind("plothover",function(o,i,t){if(t){if(b!==t.dataIndex){b=t.dataIndex,$("#chart-tooltip").remove();var l=(t.datapoint[0],t.datapoint[1]);g=0===t.seriesIndex?"$ <strong>"+l+"</strong>":1===t.seriesIndex?"<strong>"+l+"</strong> sales":"<strong>"+l+"</strong> tickets",$('<div id="chart-tooltip" class="chart-tooltip">'+g+"</div>").css({top:t.pageY-45,left:t.pageX+5}).appendTo("body").show()}}else $("#chart-tooltip").remove(),b=null}),$.plot(e,[{label:"Tickets",data:p},{label:"Sales",data:n},{label:"Earnings",data:c}],{colors:["#aaaaaa","#454e59","#5ccdde"],series:{stack:!0,lines:{show:!0,fill:!0}},lines:{show:!0,lineWidth:0,fill:!0,fillColor:{colors:[{opacity:.6},{opacity:.6}]}},legend:{show:!0,position:"nw",sorted:!0,backgroundOpacity:0},grid:{borderWidth:0},yaxis:{tickColor:"#f5f5f5",ticks:3},xaxis:{ticks:h,tickColor:"#f5f5f5"}}),$.plot(r,[{label:"Sales",data:30},{label:"Tickets",data:10},{label:"Earnings",data:60}],{colors:["#454e59","#5cafde","#5ccdde"],legend:{show:!1},series:{pie:{show:!0,radius:1,label:{show:!0,radius:2/3,formatter:function(o,i){return'<div class="chart-pie-label">'+o+"<br>"+Math.round(i.percent)+"%</div>"},background:{opacity:.75,color:"#000000"}}}}}),$.plot(s,[{label:"Sales Before",data:d,bars:{show:!0,lineWidth:0,fillColor:{colors:[{opacity:.6},{opacity:.6}]}}},{label:"Sales After",data:f,bars:{show:!0,lineWidth:0,fillColor:{colors:[{opacity:.6},{opacity:.6}]}}}],{colors:["#5ccdde","#454e59"],legend:{show:!0,position:"nw",backgroundOpacity:0},grid:{borderWidth:0},yaxis:{ticks:3,tickColor:"#f5f5f5"},xaxis:{ticks:u,tickColor:"#f5f5f5"}})}}}();