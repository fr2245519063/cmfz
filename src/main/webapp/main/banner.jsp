<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>
    $(function () {
        $("#bannerList").jqGrid({
            url: "${path}/banner/findAll",
            editurl: "${path}/banner/edit",
            datatype: "json",
            colNames: ["ID", "描述", "标题", "创建时间", "状态", "img"],
            colModel: [
                {name: "id"},
                {name: "des", editable: true},
                {name: "title", editable: true},
                {name: "create_date"},
                {
                    name: "status", editable: true, edittype: 'select',
                    editoptions: {value: '展示:展示;不展示:不展示'}
                },
                {
                    name: "img", editable: true, edittype: "file",reloadAfterSubmit:true,
                    formatter: function (a, b, c) {
                        return "<img style='width:100px;height:40px%' src='${path}/img/" + a + "'/>"
                    }
                }
            ],
            styleUI: "Bootstrap",
            autowidth: true,
            height: "60%",
            pager: "#bannerPager",
            page: 1,
            rowNum: 4,
            rowList: [2, 4, 6],
            viewrecords: true,
            multiselect: true
        }).jqGrid("navGrid", "#bannerPager",
            {//处理页面几个按钮的样式
                search: true,
                clearAfterAdd:true,
                closeAfterEdit:true,
                eloadAfterSubmit:true,
            },
            {//在编辑之前或者之后进行额外的操作
                /*beforeShowForm:function () {
                    alert("1")
                }*/
                closeAfterEdit: true,
                clearAfterAdd:true,
                closeAfterEdit:true,
                beforeShowForm: function (obj) {
                    obj.find("#des").attr("readonly", true);
                },
                afterSubmit: function (response) {
                    var bannerId = response.responseText;
                    $.ajaxFileUpload({
                        url: "${path}/banner/upload",
                        fileElementId: "img",
                        data: {bannerId: bannerId},
                        success: function (data) {
                            ("#bannerList").trigger("reloadGrid");
                        }
                    });
                    return response;

                },
                reloadAfterSubmit:true
            },
            {//在添加数据 之前或者之后进行额外的操作
                /*beforeShowForm:function () {
                    alert("2")
                }*/
                closeAfterAdd: true,
                clearAfterAdd:true,
                //修改完之后自动关闭框
                closeAfterEdit:true,
                afterSubmit: function (response) {
                    var bannerId = response.responseText;
                    $.ajaxFileUpload({
                        url: "${path}/banner/upload",
                        fileElementId: "img",
                        data: {bannerId: bannerId},
                        success: function (data) {
                            ("#bannerList").trigger("reloadGrid");
                        }
                    });
                    return response;
                },
                reloadAfterSubmit:true,

            },
            {//在删除数据之前或者之后进行额外的操作
                /*beforeShowForm:function () {
                    alert("3")
                }*/
            }
        )

    })

    function showModal() {
        location.href = "${path}/banner/queryAll"
    }
</script>

<div>

    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab"
                                                  data-toggle="tab">轮播图列表</a></li>
        <li role="presentation"><a href="#profile" onclick="showModal()" aria-controls="profile" role="tab"
                                   data-toggle="tab">导出</a></li>
    </ul>

</div>
<table id="bannerList">

</table>

<div id="bannerPager" style="height: 50px"></div>