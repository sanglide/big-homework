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
                console.log(data);
                nameList = data.map(function (item) {
                    return item.name;
                });
                console.log(nameList);
                var boxingStr='';
                console.log(nameList.length);
                for(var i=0;i<nameList.length;i++){
                    if(i==0||i==1||i==2){
                        boxingStr=boxingStr+'<div class="statistic-item">'+
                            '<span class="icon-fire">'+nameList[i]+'</span>'+
                            '<span class="badge">'+(i+1).toString()+'</span>'+
                            '</div>'
                    }
                    else {
                        boxingStr = boxingStr + '<div class="statistic-item">' +
                            '<span class="icon-thumbs-up">' + nameList[i] + '</span>' +
                            '<span class="error-text">' + (i+1).toString() + '</span>' +
                            '</div>'
                    }
                }
                console.log(boxingStr);
                $("#boxingOffice").append(boxingStr);
            },
            function (error) {
                alert(JSON.stringify(error));
            });
        //
    }
    function getPopularMovie(){
        var num=0;
        console.log(num);
    }
});