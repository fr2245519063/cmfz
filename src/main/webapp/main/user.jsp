<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>
    $(function () {
        $("#userList").jqGrid({
            url: "${path}/user/findByPage",
            editurl: "${path}/user/edit",
            datatype: "json",
            colNames: ["ID", "法名", "真实姓名", "性别", "城市","签名","用户名","密码","状态", "创建时间","头像"],
            colModel: [
                {name: "id"},
                {name: "faname", editable: true},
                {name: "names", editable: true},
                {name: "sex",editable:true,edittype: 'select',
                    editoptions: {value: '男:男;女:女'}},
                {name: "city", editable: true},
                {name:"sign",editable: true},
                {name:"username",editable:true},
                {name:"password",editable:true},
                {name:"status",editable:true,edittype: 'select',
                    editoptions: {value: '激活:激活;未激活:未激活'}},
                {name:"newdata"},
                {
                    name: "head_img", editable: true, edittype: "file",reloadAfterSubmit:true,
                    formatter: function (a, b, c) {
                        return "<img style='width:100px;height:40px%' src='${path}/img/" + a + "'/>"
                    }
                }
            ],
            styleUI: "Bootstrap",
            autowidth: true,
            height: "60%",
            pager: "#userPager",
            page: 1,
            rowNum: 4,
            rowList: [2, 4, 6],
            viewrecords: true,
            multiselect: true
        }).jqGrid("navGrid", "#userPager",
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
                        url: "${path}/user/upload",
                        fileElementId: "head_img",
                        data: {bannerId: bannerId},
                        success: function (data) {
                            ("#userList").trigger("reloadGrid");
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
                        url: "${path}/user/upload",
                        fileElementId: "head_img",
                        data: {bannerId: bannerId},
                        success: function (data) {
                            ("#userList").trigger("reloadGrid");
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

</script>

<div>

    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab"
                                                  data-toggle="tab">轮播图列表</a></li>
    </ul>

</div>
<table id="userList">

</table>

<div id="userPager" style="height: 50px"></div>