<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function () {
        $("#albumList").jqGrid({
            url: "${pageContext.request.contextPath}/album/findByPage",
            editurl: "${path}/album/edit",
            datatype: "json",
            colNames: ["ID", "标题", "评分", "作者", "播音员", "章节数", "专辑简介", "发布时间", "封面"],
            colModel: [
                {name: "id"},
                {name: "title", editable: true},
                {name: "score"},
                {name: "author", editable: true},
                {name: "beam", editable: true},
                {name: "counts", editable: true},
                {name: "content", editable: true},
                {name: "publish_date"},
                {
                    name: "img", editable: true, edittype: "file",
                    formatter: function (a, b, c) {
                        return "<img style='width:100px;height:40px%' src='${path}/img/" + a + "'/>"
                    }
                }
            ],
            styleUI: "Bootstrap",
            autowidth: true,
            height: "60%",
            pager: "#albumPager",
            page: 1,
            rowNum: 4,
            multiselect: true,
            rowList: [2, 4, 6],
            viewrecords: true,
            subGrid: true,
            subGridRowExpanded: function (subgrid_id, albumId) {
                addSubGrid(subgrid_id, albumId);
            }
        }).jqGrid("navGrid", "#albumPager",
            {
                search: false
            }, {
                closeAfterEdit: true,
                beforeShowForm: function (obj) {
                    obj.find("#cover").attr("disabled", true);
                    obj.find("#score").attr("readonly", true);
                },
                afterSubmit: function (response) {
                    var bannerId = response.responseText;
                    $.ajaxFileUpload({
                        url: "${path}/album/upload",
                        fileElementId: "img",
                        data: {bannerId: bannerId},
                        success: function (data) {
                            ("#albumList").trigger("reloadGrid");
                        }
                    });
                    return response

                }
            }, {
                closeAfterAdd: true,
                afterSubmit: function (response) {
                    var bannerId = response.responseText;
                    $.ajaxFileUpload({
                        url: "${path}/album/upload",
                        fileElementId: "img",
                        data: {bannerId: bannerId},
                        success: function (data) {
                            $("#albumList").trigger("reloadGrid");
                        }
                    });
                    return response
                }
            }, {}
        )
    });

    function addSubGrid(subgrid_id, albumId) {
        var tableId = subgrid_id + "table";
        var divId = subgrid_id + "div";
        $("#" + subgrid_id).html(
            "<table id='" + tableId + "' ></table>" + "<div id='" + divId + "' ></div>"
        );
        $("#" + tableId).jqGrid({
            url: "${path}/chapter/findAll?albumId=" + albumId,
            editurl: "${path}/chapter/edit?albumId=" + albumId,
            datatype: "json",
            colNames: ["ID", "标题", "大小", "时长", "状态", "音频路径", "操作"],
            colModel: [
                {name: "id"},
                {name: "title", editable: true},
                {name: "size"},
                {name: "longs"},
                {name: "status"},
                {name: "filepath", editable: true, edittype: "file"},
                {
                    name: "filepath",
                    formatter: function (cellValue, options, rowObject) {
                        return "<a onclick=\"playAudio('" + rowObject.filepath + "')\" href='#'><span class='glyphicon glyphicon-play-circle'></span></a>" + "            " +
                            "<a onclick=\"downloadAudio('" + rowObject.filepath + "')\" href='#'><span class='glyphicon glyphicon-download'></span></a>"
                    }
                }
            ],
            styleUI: "Bootstrap",
            autowidth: true,
            height: "60%",
            pager: "#" + divId,
            page: 1,
            rowNum: 2,
            multiselect: true,
            rowList: [2, 4, 6],
            viewrecords: true
        }).jqGrid("navGrid", "#" + divId,
            {
                search: false
            },
            {},
            {
                closeAfterAdd: true,
                afterSubmit: function (response) {
                    console.log(albumId)
                    var chapterId = response.responseText;
                    $.ajaxFileUpload({
                        url: "${path}/chapter/upload?albumId=" + albumId,
                        fileElementId: "filepath",
                        data: {chapterId: chapterId},
                        success: function (data) {
                            $("#" + tableId).trigger("reloadGrid");
                            $("#albumList").trigger("reloadGrid");
                        }
                    });
                    return response
                }
            },
            {
                afterSubmit: function () {
                    $("#" + tableId).trigger("reloadGrid");
                    $("#albumList").trigger("reloadGrid");
                    return "adf";
                }
            }
        )

    }

    function playAudio(d) {
        $("#dialogId").modal("show");
        $("#audioId").attr("src", "${path}/audio/" + d)
    }

    function downloadAudio(a) {
        location.href = "${path}/chapter/download?audioName=" + a;
    }
</script>


<div id="dialogId" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <audio id="audioId" controls src=""></audio>
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<table id="albumList"></table>
<div id="albumPager" style="height: 50px"></div>