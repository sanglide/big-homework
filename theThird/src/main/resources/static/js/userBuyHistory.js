$(document).ready(function () {
    //这个页面是用户的购买记录
    getMovieList();

    function getMovieList() {
        // getRequest(
        //     '/ticket/get/' + sessionStorage.getItem('id'),
        //
        //     function (res) {
        //         //res.content是(List<TicketVO>)
        //         renderTicketList(res.content);
        //
        //     },
        //     function (error) {
        //         alert(error);
        //     });

        var form=[{
            id:10,
            date:"2019/1/1",
            num:"3",
            total:"46.00"
        },{
            id:2,
            date:"2019/3/3",
            num:"2",
            total:"32.00"
        }]
        renderTicketList(form);
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
            "       <div>"+history.date+"</div>" +
            "   </td>" +
            "   <td>" +
            "       <div>"+history.num+"</div>" +
            "   </td>" +
            "   <td >" +
            "       <div>"+history.total+"</div>" +
            "   </td>" +
            "   <td>" +
            "       <div>"+"<a herf='/user/movieDetail?id="+ history.id +"'>详情</a>"+"</div>" +
            "   </td>" +
            "</tr>"
        $("#myTable").append(ticketStr);
    }
});