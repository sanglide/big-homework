$(document).ready(function() {
    getBoxingRate();

    getPopularMovie();
    function getBoxingRate(){
        // var nameList=["美国队长","雷神索尔","阿斯加德"]
        // var boxOffice=[99,98,97];
        var nameList=[];
        getRequest(
            '/statistics/boxOffice/total',
            function (res) {
                var data = res.content || [];
                console.log(data)
                nameList = data.map(function (item) {
                    return item.name;
                });
                console.log(nameList);
            },
            function (error) {
                alert(JSON.stringify(error));
            });
        //

        console.log(nameList);
        var boxingStr='';
        console.log(nameList.length);
        for(var i=0;i<nameList.length;i++){
            boxingStr=boxingStr+'<div class="statistic-item">'+
                '<span>'+setTimeout(nameList[i],3)+'</span>'+
                '<span class="error-text">'+1+'</span>'+
                '</div>'
            console.log(boxingStr);
        }
        console.log(boxingStr);
        $("#boxingOffice").append(boxingStr);
    }
    function getPopularMovie(){
        var num=0;
        console.log(num);
    }
})