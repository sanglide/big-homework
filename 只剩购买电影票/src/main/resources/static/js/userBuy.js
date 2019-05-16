$(document).ready(function () {
    //这个页面是用户的电影票界面
    getMovieList();

    function getMovieList() {
        getRequest(
            '/ticket/get/{userId}' + sessionStorage.getItem('id'),
            function (res) {
                renderTicketList(res.content);
            },
            function (error) {
                alert(error);
            });

//        list=[{
//        'name':"夏目友人帐",
//        'status':"2",
//        'startTime':"13:00",
//        'endTime':"15:00",
//        'location':"1号厅",
//        'price':"34",
//        'seat':"6 7"}]
        renderTicketList(list);
    }

    // TODO:填空
    function renderTicketList(list) {
        $('.ticket-on-list').empty();
        var movieDomStr = '';
        list.forEach(function (movie) {
            movie.description = movie.description || '';
            movieDomStr +=
                "<li class='movie-item card'>" +
                "<div class='movie-info'>" +
                "<div class='movie-title'>" +
                "<span class='primary-text'>" + movie.name + "</span>" +
                "<span class='label "+(!movie.status ? 'primary-bg' : 'error-bg')+"'>" + (movie.status=="0" ? '未完成' : (movie.status=="1"?'已完成':'已失效')) + "</span>" +
                "</div>" +
                "<div class='movie-description dark-text'><span>时间：" + movie.startTime + "</span><span>到" + movie.endTime + "</span></div>" +
                "<div>影厅名称：" + movie.location + "</div>" +
                "<div style='display: flex'><span>票价：" + movie.price + "</span><span style='margin-left: 30px;'>座位：" + movie.seat + "</span>" +
                "</div>" +
                "</div>"+
                "</li>";
        });
        $('.ticket-on-list').append(movieDomStr);
    }

});