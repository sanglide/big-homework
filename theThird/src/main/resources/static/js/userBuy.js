$(document).ready(function () {
    //这个页面是用户的电影票界面
    //用户的电影票中需要有：
    //电影名称、影厅名、座位、放映时间、预计结束时间、状态
    getMovieList();
    
    function getMovieList() {
        getRequest(
            //{}的情况参照movieDetail.js的13行和MovieController.java的59行
            //给后端传一个int型的userId
            //获得用户买过的票
            '/ticket/get/' + sessionStorage.getItem('id'),

            function (res) {
                //res.content是(List<TicketVO>)
                renderTicketList(res.content);
                console.log(res.content);

            },
            function (error) {
                alert(error);
            });
    }

    // TODO:填空
    function renderTicketList(list) {
        //list中的元素是TicketVO,是用户买过的所有票
        list.forEach(function(ticket){
            console.log(ticket);
            //ticket是TicketVO类型的
            //要根据scheduleId获得一次购票中选定的座位
            //根据电影id和用户id获得电影名称
            getRequest(
                "/schedule/"+ticket.scheduleId,
                function(res){
                    //res.content为ScheduleItemVO
                    var ticketInfo=res.content;
                    console.log(ticketInfo);
                    fillTable(ticket,ticketInfo);
                },
                function(error){
                    alert(error);
                }
            )
        })
    }

    function fillTable(ticket,ticketInfo){
        var ticketStr =
            "<tr>" +
            "   <td>" +
            "       <div>"+ticketInfo.movieName+"</div>" +
            "   </td>" +
            "   <td>" +
            "       <div>"+ticketInfo.hallName+"</div>" +
            "   </td>" +
            "   <td >" +
            "       <div>"+(ticket.rowIndex+1)+"排"+(ticket.columnIndex+1)+"座"+"</div>" +
            "   </td>" +
            "   <td>" +
            "       <div>"+ticketInfo.startTime.substring(5, 7) + "月" + ticketInfo.startTime.substring(8, 10) + "日 " + ticketInfo.startTime.substring(11, 16)+"</div>" +
            "   </td>" +
            "   <td>" +
            "       <div>"+ticketInfo.endTime.substring(5, 7) + "月" + ticketInfo.endTime.substring(8, 10) + "日 " + ticketInfo.endTime.substring(11, 16)+"</div>" +
            "   </td>" +
            "   <td>" +
            "       <div>"+ticket.state+"</div>" +
            "   </td>" +
            "   <td>" +
            "       <div>" +
            "           <input type='checkbox' name='category' data-refund='"+JSON.stringify(ticket)+"'value='"+ticketInfo.movieName+"'/>"+//复选框
            "       </div>"+
            "   </td>"+
            "</tr>";
        $("#myTable").append(ticketStr);
    }

    //里面是选中要退票的电影票id
    var selectedTicketsId=[];

    $(document).on('click',"input[type='checkbox']",function (e) {
        console.log(this);
        console.log($(this).prop('checked'));//Jquery更新后，复选框的checked属性要用prop设置和查看！不能用attr
        var refund=JSON.parse(e.currentTarget.dataset.refund);
        console.log(refund);
        //refund.id是电影票的id
        if($(this).prop('checked')){
            selectedTicketsId.push(refund.id);
        }else{
            if(selectedTicketsId.indexOf(refund.id)>-1){
                selectedTicketsId.splice(selectedTicketsId.indexOf(refund.id),1);
            }
        }
        console.log(selectedTicketsId);

    })

    $(document).on('click','.refund-item',function (e) {
        var r=confirm("请问真的要退这些票吗？");
            if(r){
                deleteRequest(
                    //传电影票ID
                    "/ticket/refund",
                    {selectedTicketsList:selectedTicketsId},
                    function (res) {
                        if(res.success){
                           getMovieList();
                            alert("删除成功！");
                        } else {
                            alert(res.message);
                        }
                    },
                    function (error) {
                        alert(JSON.stringify(error));
                    }
                )
            }
    });

});