$(document).ready(function () {
    //这个页面是用户的购买记录
    var number=0;
    getMovieList();

    function getMovieList() {
        getRequest(
            '/ticket/get/' + sessionStorage.getItem('id'),

            function (res) {
                //res.content是(List<TicketVO>)
                renderTicketList(res.content);

            },
            function (error) {
                alert(error);
            });

        // var form=[{
        //     ticketId:10,
        //     date:"2019/1/1",
        //     movieName:"夏目友人帐",
        //     state:"已退款",
        //     account:46.00,
        //     hallName:"1号厅",
        //     columnIndex:1,
        //     rowIndex:2
        // },{
        //     ticketId:2,
        //     date:"2019/3/3",
        //     movieName:"夏目友人帐",
        //     state:"已支付",
        //     account:32.00,
        //     hallName:"1号厅",
        //     columnIndex:2,
        //     rowIndex:3
        // }]
        // renderTicketList(form);
    }

    // TODO:填空
    function renderTicketList(list) {
        list.forEach(function(history) {
            fillTable(history);
        });
    }

    function fillTable(history){
        var ticketStr =
            "<tr data-ticket='"+history+"'>" +
            "   <td>" +
            "       <div>"+getLocalTime(history.date)+"</div>" +
            "   </td>" +
            "   <td>" +
            "       <div>"+history.movieName+"</div>" +
            "   </td>" +
            "   <td>" +
            "       <div>"+history.state+"</div>" +
            "   </td>" +
            "   <td >" +
            "       <div>"+history.account+"</div>" +
            "   </td>" +
            "   <td>" +
            "<button class='history-item' data-history='"+JSON.stringify(history)+"' >"+"详情</button>"
            "   </td>" +
            "</tr>";
        $("#myTable").append(ticketStr);
    }
    $(document).on('click','.history-item',function (e) {
        $("#modal-body").empty();
        $('#historyModal').modal('show');
        var data=JSON.parse(e.currentTarget.dataset.history);
        console.log(data);
        console.log(data.date);
        var detailStr="<div class=\"form-group\">" +
            "<div class='col-sm-10'>" +
            "<div><b>电影名称：</b><span>"+data.movieName+"</span>" +
            "</div>" +
            "<div><b>购买日期：</b><span>"+getLocalTime(history.date)+"</span>" +
            "</div>" +
            "<div><b>购买状态：</b><span>"+data.state+"</span>" +
            "</div>" +
            "<div><b>消费金额：</b><span>"+data.account+"</span>" +
            "</div>" +
            "<div><b>影厅位置：</b><span>"+data.hallName+"</span>" +
            "</div>" +
            "<div><b>座位位置：</b><span>"+data.rowIndex+"排"+data.columnIndex+"座</span>" +
            "</div>" +
            "</div>";
        $("#modal-body").append(detailStr);
    });

    function getLocalTime(nS) {
        return new Date(parseInt(nS) * 1000).toLocaleString().replace(/:\d{1,2}$/,' ');
    }


});