$(document).ready(function() {
    getBoxingRate();

    getPopularMovie();
    function getBoxingRate(){
        var nameList=["美国队长","雷神索尔","阿斯加德"]
        var boxOffice=[99,98,97];
        // getRequest(
        //     '/statistics/boxOffice/total',
        //     function (res) {
        //         var data = res.content || [];
        //         console.log(data)
        //         boxOffice = data.map(function (item) {
        //             return item.boxOffice;
        //         });
        //         nameList = data.map(function (item) {
        //             return item.name;
        //         });
        //         console.log(boxOffice);
        //         console.log(nameList);
        //     },
        //     function (error) {
        //         alert(JSON.stringify(error));
        //     });

        //上面的需要方法，可能是没有权限
        console.log(boxOffice);
        console.log(nameList);
        var boxingStr='<div class="statistic-item">'+
            '<span>'+nameList[0]+'</span>'+
            '<span class="error-text">'+boxOffice[0]+'</span>'+
            '</div>'+
            '<div class="statistic-item">'+
            '<span>'+nameList[1]+'</span>'+
            '<span class="error-text">'+boxOffice[1]+'</span>'+
            '</div>'+
            '<div class="statistic-item">'+
            '<span>'+nameList[2]+'</span>'+
            '<span class="error-text">'+boxOffice[2]+'</span>'+
            '</div>'
        console.log(boxingStr);
        $("#boxingOffice").append(boxingStr);
    }
    function getPopularMovie(){
        var num=0;
        console.log(num);
    }
}