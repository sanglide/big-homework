$(document).ready(function () {
    //这个页面是用户的电影票界面
    //用户的电影票中需要有：
    //电影名称、影厅名、座位、放映时间、预计结束时间、状态
    getMovieList();

    var canRefund;
    var can=[];

    function getMovieList() {
        getRequest(
            //{}的情况参照movieDetail.js的13行和MovieController.java的59行
            //给后端传一个int型的userId
            //获得用户的订单
            '/ticket/get/' + sessionStorage.getItem('id'),

            function (res) {
                //res.content是(List<TicketOrderVO>)
                console.log("res的content是");
                console.log(res.content);
                renderTicketList(res.content);

            },
            function (error) {
                alert(error);
            });

        //////////////////假数据///////////////////
        // var r={
        //     id:1,
        //     time:22222222222222,
        //     state:0,
        //     originCost:30.00,
        //     refund:25.00,
        //     canRefund:true,
        //     ticketVOList:[
        //         {
        //             id:11,
        //             userId:16,
        //             scheduleId:87,
        //             columnIndex:0,
        //             rowIndex:0,
        //             state:"已完成",
        //             time:44444444444444444444
        //         },
        //         {
        //             id:12,
        //             userId:16,
        //             scheduleId:87,
        //             columnIndex:1,
        //             rowIndex:1,
        //             state:"已完成",
        //             time:44444444444444444444
        //         }
        //     ]
        // }
        // renderTicketList(r);
        ///////////假数据/////////////////

    }

    // TODO:填空
    function renderTicketList(orders) {
        $("#orders").empty();
        //orders是一个list
        //orders中的元素是TicketOrderVO,是用户买过的所有订单
        //list里是用户的这一订单里电影票
        orders.forEach(function (order) {
            var list=order.ticketVOList;
            var seats='';//订单中的座位
            console.log("===================");
            console.log(order);
            list.forEach(function(ticket){
                console.log(ticket);
                seats+=(ticket.rowIndex+1)+"排"+(ticket.columnIndex+1)+"座\n";
                console.log(seats);
                //ticket是TicketVO类型的
            })

            //要根据scheduleId获得一次购票中选定的座位

            getRequest(
                "/schedule/"+list[0].scheduleId,
                function(res){
                    //ticketInfo为ScheduleItemVO
                    var ticketInfo=res.content;
                    console.log(ticketInfo);
                    fillTable(order,seats,ticketInfo);
                },
                function(error){
                    alert(error);
                }
            )

            ///////假数据////////////////
            // var ticketInfo={
            //     id:0,
            //     hallId:99,
            //     hallName:"哈哈厅",
            //     movieId:555,
            //     movieName:"哈哈哈哈嗝",
            //     startTime:"2019-06-13 12:45",
            //     endTime:"2019-06-13 22:33",
            //     fare:45,
            // }
            // fillTable(order,seats,ticketInfo);
            //////假数据///////////////////////
        })
    }

    function fillTable(order,seats,ticketInfo){
        var ticketStr =
            "<tr>" +
            "   <td>" +
            "       <div>"+order.id+"</div>" +
            "   </td>" +
            "   <td>" +
            "       <div>"+ticketInfo.movieName+"</div>" +
            "   </td>" +
            "   <td>" +
            "       <div>"+ticketInfo.hallName+"</div>" +
            "   </td>" +
            "   <td >" +
            "       <div>"+seats+"</div>" +
            "   </td>" +
            "   <td>" +
            "       <div>"+ticketInfo.startTime.substring(5, 7) + "月" + ticketInfo.startTime.substring(8, 10) + "日 " + ticketInfo.startTime.substring(11, 16)+"</div>" +
            "   </td>" +
            "   <td>" +
            "       <div>"+ticketInfo.endTime.substring(5, 7) + "月" + ticketInfo.endTime.substring(8, 10) + "日 " + ticketInfo.endTime.substring(11, 16)+"</div>" +
            "   </td>" +
            "   <td>" +
            "       <div>"+order.state+"</div>" +
            "   </td>" +
            "   <td>" +
            "       <div>"+order.originCost+"</div>" +
            "   </td>" +
            "   <td>" +
            "       <div>"+order.refund+"</div>" +
            "   </td>" +
            "   <td>" +
            "       <div>" +
            "           <input type='checkbox' name='category' data-refund='"+JSON.stringify(order)+"'value='"+ticketInfo.movieName+"'/>"+//复选框
            "       </div>"+
            "   </td>"+
            "</tr>";
        $("#myTable").append(ticketStr);
    }

    //里面是选中要退的订单id
    var selectedTicketsId=[];

    $(document).on('click',"input[type='checkbox']",function (e) {
        console.log(this);
        console.log($(this).prop('checked'));//Jquery更新后，复选框的checked属性要用prop设置和查看！不能用attr
        var refund=JSON.parse(e.currentTarget.dataset.refund);
        console.log(refund);
        //refund.id是电影票的id
        if($(this).prop('checked')){
            can.push(refund.canRefund);
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
        console.log("cansnsnsnsns");
        console.log(can);
        canRefund=true;
        if(can.indexOf(false)!==-1){
            canRefund=false;
        }

        console.log("--------------");
        console.log(canRefund);
            if(r){
                console.log("eeeeeeeeeeeeee");
                console.log(selectedTicketsId);
                if(selectedTicketsId.length===0){
                    alert("请选择需要退的订单");
                }else{
                    if(!canRefund){
                        alert("退票不合规范，请重新选择");
                        can=[];
                    }else{
                        getRequest(
                            //传电影票ID
                            "/ticket/refund?idList="+selectedTicketsId,
                            // {selectedTicketsList:selectedTicketsId},
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

                }

            }
    });

});