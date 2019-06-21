$(document).ready(function() {


    getScheduleRate();

    getBoxOffice();

    getAudiencePrice();

    getPlacingRate();

    getPolularMovie();

    function getScheduleRate() {

        getRequest(
            '/statistics/scheduleRate',
            function (res) {
                var data = res.content||[];
                var tableData = data.map(function (item) {
                   return {
                       value: item.time,
                       name: item.name
                   };
                });
                var nameList = data.map(function (item) {
                    return item.name;
                });
                var option = {
                    title : {
                        text: '今日排片率',
                        subtext: new Date().toLocaleDateString(),
                        x:'center'
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        x : 'center',
                        y : 'bottom',
                        data:nameList
                    },
                    toolbox: {
                        show : true,
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            magicType : {
                                show: true,
                                type: ['pie', 'funnel']
                            },
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    calculable : true,
                    series : [
                        {
                            name:'面积模式',
                            type:'pie',
                            radius : [30, 110],
                            center : ['50%', '50%'],
                            roseType : 'area',
                            data:tableData
                        }
                    ]
                };
                var scheduleRateChart = echarts.init($("#schedule-rate-container")[0]);
                scheduleRateChart.setOption(option);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function getBoxOffice() {

        getRequest(
            '/statistics/boxOffice/total',
            function (res) {
                var data = res.content || [];
                var tableData = data.map(function (item) {
                    return item.boxOffice;
                });
                var nameList = data.map(function (item) {
                    return item.name;
                });
                console.log(tableData);
                console.log(nameList);
                var option = {
                    title : {
                        text: '所有电影票房',
                        subtext: '截止至'+new Date().toLocaleDateString(),
                        x:'center'
                    },
                    xAxis: {
                        type: 'category',
                        data: nameList
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        data: tableData,
                        type: 'bar'
                    }]
                };
                var scheduleRateChart = echarts.init($("#box-office-container")[0]);
                scheduleRateChart.setOption(option);
            },
            function (error) {
                alert(JSON.stringify(error));
            });
    }

    function getAudiencePrice() {
        getRequest(
            '/statistics/audience/price',
            function (res) {
                var data = res.content || [];
                var tableData = data.map(function (item) {
                    return item.price;
                });
                var nameList = data.map(function (item) {
                    return formatDate(new Date(item.date));
                });
                var option = {
                    title : {
                        text: '每日客单价',
                        x:'center'
                    },
                    xAxis: {
                        type: 'category',
                        data: nameList
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        data: tableData,
                        type: 'line'
                    }]
                };
                var scheduleRateChart = echarts.init($("#audience-price-container")[0]);
                scheduleRateChart.setOption(option);
            },
            function (error) {
                alert(JSON.stringify(error));
            });
    }

    function getPlacingRate() {
        var formDate=new Date();
        var scheduleDate = formatDate(new Date());
        $('#schedule-date-input').val(scheduleDate);
        changePlacingRate(scheduleDate);
        console.log(scheduleDate.toString());
        $('#schedule-date-input').change(function () {
            scheduleDate = $('#schedule-date-input').val();
            formDate=new Date(scheduleDate.replace("-","/"));
            changePlacingRate(scheduleDate);
        });
    }
    function changePlacingRate(date){
        var dateString=date.replace(/-/g,"/");
        console.log(dateString);
        getRequest(

            '/statistics/PlacingRate?date='+dateString,

            function (res) {
                var data = res.content || [];
                var tableData = data.map(function (item) {
                    return item.placingRate;
                    //
                });
                var nameList = data.map(function (item) {
                    return item.name;
                });
                var option = {
                    title : {
                        text: '上座率',
                        x:'center'
                    },
                    xAxis: {
                        type: 'category',
                        data: nameList
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        data: tableData,
                        type: 'bar'
                    }]
                };
                var scheduleRateChart = echarts.init($("#place-rate-container")[0]);
                scheduleRateChart.setOption(option);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function getPolularMovie() {
        // todo
        var daysNum=30,movieNum=4;
        changePopularMovie(movieNum,daysNum);
        $("#days-num").text(daysNum);
        $("#movie-num").text(movieNum);
        $('#days-modify-btn').click(function () {
            $("#days-modify-btn").hide();
            $("#days-set-input").val(daysNum);
            $("#days-set-input").show();
            $("#days-confirm-btn").show();
        });
        $('#movie-modify-btn').click(function () {
            $("#movie-modify-btn").hide();
            $("#movie-set-input").val(movieNum);
            $("#movie-set-input").show();
            $("#movie-confirm-btn").show();
        });
        $('#days-confirm-btn').click(function () {
            daysNum = $("#days-set-input").val();
            $("#days-num").text(daysNum);
            $("#days-modify-btn").show();
            $("#days-set-input").hide();
            $("#days-confirm-btn").hide();
            changePopularMovie(movieNum,daysNum);
        });
        $('#movie-confirm-btn').click(function () {
            movieNum = $("#movie-set-input").val();
            $("#movie-num").text(movieNum);
            $("#movie-modify-btn").show();
            $("#movie-set-input").hide();
            $("#movie-confirm-btn").hide();
            changePopularMovie(movieNum,daysNum);
        });
    }
    //
    function changePopularMovie(movieNum,daysNum){
        getRequest(
            '/statistics/popular/movie?days='+daysNum+"&movieNum="+movieNum,
            //controller的生命线的url
            function (res) {
                var data = res.content || [];
                console.log(data);
                var tableData = data.map(function (item) {
                    return item.boxOffice;
                    //
                });
                var nameList = data.map(function (item) {
                    return item.name;
                });
                var option = {
                    title : {
                        text: '最受欢迎电影',
                        subtext: '截止至'+new Date().toLocaleDateString(),
                        x:'center'
                    },
                    xAxis: {
                        type: 'category',
                        data: nameList
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        data: tableData,
                        type: 'bar'
                    }]
                };
                var scheduleRateChart = echarts.init($("#popular-movie-container")[0]);
                scheduleRateChart.setOption(option);
            },
            function (error) {
                alert(JSON.stringify(error));
            });
    }
});