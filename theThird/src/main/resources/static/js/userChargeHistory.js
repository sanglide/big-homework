$(document).ready(function () {
    //这个页面是用户的购买记录
    getMovieList();

    function getMovieList() {
        getRequest(
            '/vip/' + sessionStorage.getItem('id')+"/getChargeHistory",

            function (res) {
                //res.content是(List<TicketVO>)
                console.log(res.content);
                renderTicketList(res.content);

            },
            function (error) {
                alert(error);
            });

        // var form=[{
        //     id:1,
        //     date:"2019/1/1",
        //     total:"46.00"
        // },{
        //     id:2,
        //     date:"2019/3/3",
        //     total:"32.00"
        // }]
        // renderTicketList(form);
    }

    // TODO:填空
    function renderTicketList(list) {
        list.forEach(function(history) {
            console.log(history);
            fillTable(history);
        });
    }

    function fillTable(history){
        var ticketStr =
            "<tr>" +
            "   <td>" +
            "       <div>"+history.time.toString().substring(0,10)+"</div>" +
            "   </td>" +
            "   <td >" +
            "       <div>"+history.balance+"</div>" +
            "   </td>" +
            "   <td >" +
            "       <div>"+history.charge+"</div>" +
            "   </td>" +
            "</tr>"
        $("#myTable").append(ticketStr);
    }
    function getLocalTime(nS) {
        return new Date(parseInt(nS) * 1000).toLocaleString().replace(/:\d{1,2}$/,' ');
    }
});