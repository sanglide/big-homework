$(document).ready(function() {

    getAllMovies();

    getActivities();

    function getActivities() {
        getRequest(
            '/activity/get',
            function (res) {
                var activities = res.content;
                renderActivities(activities);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function renderActivities(activities) {
        $(".content-activity").empty();
        var activitiesDomStr = "";

        activities.forEach(function (activity) {
            //activity是activityVO
            var movieDomStr = "";
            //优惠电影列表
            if(activity.movieList.length){
                activity.movieList.forEach(function (movie) {
                    movieDomStr += "<li class='activity-movie primary-text'>"+movie.name+"</li>";
                });
            }else{
                movieDomStr = "<li class='activity-movie primary-text'>所有热映电影</li>";
            }

            activitiesDomStr+=
                "<div class='activity-container' data-activity='"+JSON.stringify(activity)+"'>" +
                "    <div class='activity-card card' >" +
                "       <div class='activity-line'>" +
                "           <span class='title'>"+activity.name+"</span>" +
                "           <span class='gray-text'>"+activity.description+"</span>" +
                "       </div>" +
                "       <div class='activity-line'>" +
                "           <span>活动时间："+formatDate(new Date(activity.startTime))+"至"+formatDate(new Date(activity.endTime))+"</span>" +
                "       </div>" +
                "       <div class='activity-line'>" +
                "           <span>参与电影：</span>" +
                "               <ul>"+movieDomStr+"</ul>" +
                "       </div>" +
                "    </div>" +
                "    <div class='activity-coupon primary-bg'>" +
                "        <span class='title'>优惠券："+activity.coupon.name+"</span>" +
                "        <span class='title'>满"+activity.coupon.targetAmount+"减<span class='error-text title'>"+activity.coupon.discountAmount+"</span></span>" +
                "        <span class='gray-text'>"+activity.coupon.description+"</span>" +
                "    </div>" +
                "</div>";
        });
        $(".content-activity").append(activitiesDomStr);
    }

    function getAllMovies() {
        getRequest(
            //获得未下架的所有电影
            '/movie/all/exclude/off',
            function (res) {
                var movieList = res.content;
                $('#activity-movie-input').append("<option value="+ -1 +">所有电影</option>");
                movieList.forEach(function (movie) {
                    $('#activity-movie-input').append("<option value="+ movie.id +">"+movie.name+"</option>");
                    $('#activity-edit-movie-input').append("<option value="+ movie.id +">"+movie.name+"</option>");
                });
            },
            function (error) {
                alert(error);
            }
        );
    }

    //发布优惠券表单的确认按钮
    $("#activity-form-btn").click(function () {
        var form = {
            name: $("#activity-name-input").val(),
            description: $("#activity-description-input").val(),
            startTime: $("#activity-start-date-input").val(),
            endTime: $("#activity-end-date-input").val(),
            movieList: [...selectedMovieIds],
            couponForm: {
                description: $("#coupon-description-input").val(),
                name: $("#coupon-name-input").val(),
                targetAmount: $("#coupon-target-input").val(),
                discountAmount: $("#coupon-discount-input").val(),
                startTime: $("#activity-start-date-input").val(),
                endTime: $("#activity-end-date-input").val()
            }
        };

        if(!validateActivityForm(form)){
            return;
        }

        postRequest(
            '/activity/publish',
            form,
            function (res) {
                if(res.success){
                    getActivities();
                    $("#activityModal").modal('hide');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    //ES6新api 不重复集合 Set
    var selectedMovieIds = new Set();
    var selectedMovieNames = new Set();

    $('#activity-movie-input').change(function () {
        var movieId = $('#activity-movie-input').val();
        var movieName = $('#activity-movie-input').children('option:selected').text();
        if(movieId==-1){
            selectedMovieIds.clear();
            selectedMovieNames.clear();
        } else {
            selectedMovieIds.add(movieId);
            selectedMovieNames.add(movieName);
        }
        renderSelectedMovies();
    });

    //渲染选择的参加活动的电影
    function renderSelectedMovies() {
        $('#selected-movies').empty();
        var moviesDomStr = "";
        selectedMovieNames.forEach(function (movieName) {
            moviesDomStr += "<span class='label label-primary'>"+movieName+"</span>";
        });
        $('#selected-movies').append(moviesDomStr);
    }

    //修改活动！！!
    var selectedEditMovieIds = new Set();
    var selectedEditMovieNames = new Set();

    $('#activity-edit-movie-input').change(function () {
        var movieId = $('#activity-edit-movie-input').val();
        var movieName = $('#activity-edit-movie-input').children('option:selected').text();
        if(movieId==-1){
            selectedEditMovieIds.clear();
            selectedEditMovieNames.clear();
        } else {
            selectedEditMovieIds.add(movieId);
            selectedEditMovieNames.add(movieName);
        }
        renderSelectedEditMovies();
    });

    //渲染选择的参加活动的电影
    function renderSelectedEditMovies() {
        $('#selected-edit-movies').empty();
        var moviesDomStr = "";
        selectedEditMovieNames.forEach(function (movieName) {
            moviesDomStr += "<span class='label label-primary'>"+movieName+"</span>";
        });
        $('#selected-edit-movies').append(moviesDomStr);
    }


    $(document).on('click','.activity-container',function (e) {
        console.log("============");
        console.log(e);
        //e.target是点击的元素，而不是一整块优惠券卡！！！
        //e.currentTarget范围比e.target大
        console.log(e.target);
        console.log(e.currentTarget);
        //JSON.parse很重要！！！转换！！!
        console.log(JSON.parse(e.currentTarget.dataset.activity));
        console.log(JSON.parse(e.currentTarget.dataset.activity).movieList);

        var activity=JSON.parse(e.currentTarget.dataset.activity);

        $("#activity-edit-name-input").text(""+activity.name);
        $("#activity-edit-description-input").text(activity.description);
        $("#activity-edit-start-date-input").val(activity.startTime.slice(0,10));
        $("#activity-edit-end-date-input").val(activity.endTime.slice(0,10));
        $("#coupon-edit-name-input").text(""+activity.coupon.name);
        $("#coupon-edit-description-input").text(activity.coupon.description);
        $("#coupon-edit-target-input").val(activity.coupon.targetAmount);
        $("#coupon-edit-discount-input").val(activity.coupon.discountAmount);

        $('#selected-edit-movies').empty();
        var moviesDomStr = "";
        activity.movieList.forEach(function (movie) {
            moviesDomStr += "<span class='label label-primary'>"+movie.name+"</span>";
        });
        $('#selected-edit-movies').append(moviesDomStr);

        //活动id
        $('#activityEditModal')[0].dataset.activityId = activity.id;
        console.log("--------");
        console.log($('#activityEditModal')[0].dataset);

        $("#activityEditModal").modal('show');
    })

    //修改活动的确认按钮
    $("#activity-edit-form-btn").click(function () {
        var form = {
            id:Number($('#activityEditModal')[0].dataset.activityId),
            name: $("#activity-edit-name-input").text(),
            description: $("#activity-edit-description-input").text(),
            startTime: $("#activity-edit-start-date-input").val(),
            endTime: $("#activity-edit-end-date-input").val(),
            movieList: [...selectedEditMovieIds],
            couponForm: {
                description: $("#coupon-edit-description-input").text(),
                name: $("#coupon-edit-name-input").text(),
                targetAmount: $("#coupon-edit-target-input").val(),
                discountAmount: $("#coupon-edit-discount-input").val(),
                startTime: $("#activity-edit-start-date-input").val(),
                endTime: $("#activity-edit-end-date-input").val()
            }
        };

        console.log("================");
        console.log(form);
        if(!validateActivityEditForm(form)){
            return;
        }

        console.log("!!!!!!!!!!!!!!!!!!!!");
        console.log(form);

        postRequest(
            '/activity/update',
            form,
            function (res) {
                if(res.success){
                    console.log("---------------");
                    console.log(form);
                    getActivities();
                    $("#activityEditModal").modal('hide');
                    alert("修改优惠活动成功！")
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    })

    $("#activity-edit-remove-btn").click(function () {
        var r=confirm("确认要删除该活动信息吗")
        if (r) {
            deleteRequest(
                '/activity/delete/batch',
                {activityIdList:[Number($('#activityEditModal')[0].dataset.activityId)]},
                function (res) {
                    if(res.success){
                        getActivities();
                        $("#activityEditModal").modal('hide');
                        alert("删除优惠活动成功！")
                    } else{
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
        }
    })

    function validateActivityForm(data){
        var isValidate=true;
        if(!data.name){
            isValidate=false;
            $("#activity-name-error").css("visibility","visible");
            $("#activity-name-error").text("请输入活动名称");
        }
        if(!data.startTime){
            isValidate=false;
            $("#activity-start-time-error").css("visibility","visible");
            $("#activity-start-time-error").text("请输入活动开始日期");
        }
        if(!data.endTime){
            isValidate=false;
            $("#activity-end-time-error").css("visibility","visible");
            $("#activity-end-time-error").text("请输入活动结束日期");
        }
        if(!data.couponForm.name){
            isValidate=false;
            $("#coupon-name-error").css("visibility","visible");
            $("#coupon-name-error").text("请输入优惠券名称");
        }if(!data.couponForm.description){
            isValidate=false;
            $("#coupon-description-error").css("visibility","visible");
            $("#coupon-description-error").text("请输入优惠券描述");
        }
        function isAValidNum(f){
            var isValid=false;
            if(/^[0-9]+$/.test(f)||/^[0-9]+\.?[0-9]+?$/.test(f)){
                isValid=true;
            }
            return isValid;
        }
        if(!data.couponForm.targetAmount||data.couponForm.targetAmount<0||!isAValidNum(data.couponForm.targetAmount)){
            //不存在或者为负数或者(不是整数且不是小数)
            isValidate=false;
            $('#activity-target-error').css("visibility", "visible");
            $("#activity-target-error").text("请正确输入非负整数或非负小数");
            //showErrorMessage()
        }
        if(!data.couponForm.discountAmount||data.couponForm.discountAmount<0||!isAValidNum(data.couponForm.discountAmount)){
            //不存在或者为负数或者(不是整数且不是小数)
            isValidate=false;
            $('#activity-discount-error').css("visibility", "visible");
            $("#activity-discount-error").text("请正确输入非负整数或非负小数");
            //showErrorMessage()
        }
        // if(Number(data.couponForm.discountAmount)>Number(data.couponForm.targetAmount)){
        //     isValidate=false;
        //     $('#activity-discount-error').css("visibility", "visible");
        //     $("#activity-discount-error").text("优惠金额不得大于需满金额");
        // }

        return isValidate;
    }


    function validateActivityEditForm(data){
        var isValidate=true;
        if(!data.startTime){
            isValidate=false;
            $("#activity-edit-start-time-error").css("visibility","visible");
            $("#activity-edit-start-time-error").text("请输入活动开始日期");
        }
        if(!data.endTime){
            isValidate=false;
            $("#activity-edit-end-time-error").css("visibility","visible");
            $("#activity-edit-end-time-error").text("请输入活动结束日期");
        }
        function isAValidNum(f){
            var isValid=false;
            if(/^[0-9]+$/.test(f)||/^[0-9]+\.?[0-9]+?$/.test(f)){
                isValid=true;
            }
            return isValid;
        }
        if(!data.couponForm.targetAmount||data.couponForm.targetAmount<0||!isAValidNum(data.couponForm.targetAmount)){
            //不存在或者为负数或者(不是整数且不是小数)
            isValidate=false;
            $('#activity-edit-target-error').css("visibility", "visible");
            $("#activity-edit-target-error").text("请输入非负整数或非负小数");
            //showErrorMessage()
        }
        if(!data.couponForm.discountAmount||data.couponForm.discountAmount<0||!isAValidNum(data.couponForm.discountAmount)){
            //不存在或者为负数或者(不是整数且不是小数)
            isValidate=false;
            $('#activity-edit-discount-error').css("visibility", "visible");
            $("#activity-edit-discount-error").text("请输入非负整数或非负小数");
            //showErrorMessage()
        }
        // if(Number(data.couponForm.discountAmount)>Number(data.couponForm.targetAmount)){
        //     console.log(data.couponForm);
        //     isValidate=false;
        //     $('#activity-edit-discount-error').css("visibility", "visible");
        //     $("#activity-edit-discount-error").text("优惠金额不得大于需满金额");
        // }

        return isValidate;
    }


});