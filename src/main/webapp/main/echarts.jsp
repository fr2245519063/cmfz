<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<div id="main" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            // 标题名称
            text: '持名法洲用户注册趋势图',
            //subtext: "今天明天后天"
        },
        // 工具提示
        tooltip: {},
        // 图例
        legend: {
            data:['男','女']
        },
        // X轴展示的内容  今天注册的用户数量  一周内注册的用户数量 一个月内注册的用户数量 一年内注册的用户数量
        xAxis: {
            data: ["今天","一周","一月","一年"]
        },
        // Y轴展示的内容 自适应的Y轴数据
        yAxis: {},

    };
    myChart.setOption(option);

    $.ajax({
        url: "${pageContext.request.contextPath}/user/findByCount",
        datatype: "json",
        type: "POST",
        success: function (da) {
            myChart.setOption({
                series: [{
                    name:'男',
                    type:'bar',
                    data: da.man
                },{
                    name:'女',
                    type:'bar',
                    data: da.woman
                }]
            });
        }
    })
</script>
</body>
</html>