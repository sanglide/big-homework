var colors = [
    '#FF6666',
    '#3399FF',
    '#FF9933',
    '#66cccc',
    '#FFCCCC',
    '#9966FF',
    'steelblue'
];

$(document).ready(function() {
    var hallId,//影厅Id
        scheduleDate = formatDate(new Date()),
        schedules = [];

    initSelectAndDate();

    //查询包括从当前日期开始的7天排片计划
    function getSchedules() {

        getRequest(//从指定资源请求数据//ScheduleController
            //前端要向后端传数据
            //hallId是第一个影厅的id
            //scheduleDate是当前日期
            '/schedule/search?hallId='+hallId+'&startDate='+scheduleDate.replace(/-/g,'/'),
            function (res) {
                schedules = res.content;//schedule是scheduleVO的list，每个scheduleVO里还有scheduleItemVO的list
                //schedules里是7天的排片信息
                renderScheduleTable(schedules);
             },
            function (error) {
                alert(JSON.stringify(error));
             }
        );
    }

    function renderScheduleTable(schedules){
        //将管理排片界面7天排片信息的一行日期显示全部清空
        $('.schedule-date-container').empty();
        //把该影厅7天内的排片信息清空
        $(".schedule-time-line").siblings().remove();
        //scheduleOfDate是schedule[i]
        //循环7次
        schedules.forEach(function (scheduleOfDate) {
            //往日期行添加7天的日期信息
            $('.schedule-date-container').append("<div class='schedule-date'>"+formatDate(new Date(scheduleOfDate.date))+"</div>");
            //scheduleDateDom是该影厅一天中的排片信息
            var scheduleDateDom = $(" <ul class='schedule-item-line'></ul>");
            //增加一列排片信息
            $(".schedule-container").append(scheduleDateDom);
            //下面是对一天的排片操作的
            //循环次数为当天的排片数
            scheduleOfDate.scheduleItemList.forEach(function (schedule,index) {
                //此处schedule应该是scheduleItem
                var scheduleStyle = mapScheduleStyle(schedule);
                var scheduleItemDom =$(
                    "<li id='schedule-"+ schedule.id +"' class='schedule-item' data-schedule='"+JSON.stringify(schedule)+"' style='background:"+scheduleStyle.color+";top:"+scheduleStyle.top+";height:"+scheduleStyle.height+"'>"+
                    "<span>"+schedule.movieName+"</span>"+
                    "<span class='error-text'>¥"+schedule.fare+"</span>"+
                    "<span>"+formatTime(new Date(schedule.startTime))+"-"+formatTime(new Date(schedule.endTime))+"</span>"+
                    "</li>");
                scheduleDateDom.append(scheduleItemDom);
            });
        })
    }

    //设计一列中的排片信息方块的样式
    function mapScheduleStyle(schedule) {
        //开始放映时间 小时+分钟
        var start = new Date(schedule.startTime).getHours()+new Date(schedule.startTime).getMinutes()/60,
        //结束放映时间
            end = new Date(schedule.endTime).getHours()+new Date(schedule.endTime).getMinutes()/60 ;
        return {
            color: colors[schedule.movieId%colors.length],
            top: 40*start+'px',
            height: 40*(end-start)+'px'
        }
    }

    function initSelectAndDate() {
        //排片管理中的选择日期，设置为当前日期
        $('#schedule-date-input').val(scheduleDate);//设置value
        //向服务器请求获得所有影院信息
        getCinemaHalls();
        //向服务器请求获得所有影片信息
        getAllMovies();

        // 过滤条件变化后重新查询
        //选择影厅的选择列表
        //选择不同影厅出现对应影厅的排片
        $('#hall-select').change (function () {
            hallId=$(this).children('option:selected').val();
            getSchedules();
        });
        //选择不同时期出现对应日期的排片
        $('#schedule-date-input').change(function () {
            scheduleDate = $('#schedule-date-input').val();
            getSchedules();
        });
    }
    //向服务器请求获得所有影院信息
    function getCinemaHalls() {
        var halls = [];
        //向服务器发出请求
        getRequest(
            '/hall/all',//HallController
            function (res) {
                halls = res.content;//res是一个ResponseVO,halls是HallVO的list
                hallId = halls[0].id;//hallId是第一个影厅的id（选择影厅的默认值?）
                halls.forEach(function (hall) {//hall的类型是HallVO
                    //<option> 与 <option/> 之间的值是浏览器显示在下拉列表中的内容
                    //而 value 属性中的值是表单被提交时被发送到服务器的值。
                    //在排片管理界面中的选择影厅的选择列表中添加影厅
                    $('#hall-select').append("<option value="+ hall.id +">"+hall.name+"</option>");
                    //在新增排片的表单中的放映影厅的选择列表中添加影厅
                    $('#schedule-hall-input').append("<option value="+ hall.id +">"+hall.name+"</option>");
                    //在修改排片的表单中的放映影厅的选择列表中添加影厅
                    $('#schedule-edit-hall-input').append("<option value="+ hall.id +">"+hall.name+"</option>");
                });
                getSchedules();
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    //向服务器请求获得所有电影信息
    function getAllMovies() {
        getRequest(
            '/movie/all/exclude/off',//MovieController
            function (res) {
                var movieList = res.content;//为所有正在上映的电影的列表
                movieList.forEach(function (movie) {
                    //在新增排片的表单中的电影名称的选择列表中添加电影
                    $('#schedule-movie-input').append("<option value="+ movie.id +">"+movie.name+"</option>");
                    //在修改排片的表单中的电影名称的选择列表中添加电影
                    $("#schedule-edit-movie-input").append("<option value="+ movie.id +">"+movie.name+"</option>");
                });
            },
            function (error) {
                alert(error);
            }
        );
    }

    //修改排片
    //当点击当前页面的排片信息块时，执行以下函数
    //e表示发生的事件
    $(document).on('click','.schedule-item',function (e) {
        //parse 用于从一个字符串中解析出json对象
        //用e.target指向触发事件监听的对象，获取子元素的值
        //e.target.dataset指向的是对象的属性
        //在57行时设置了data-schedule这一属性！！！
        //var schedule是点击的排片信息的ScheduleItemVO
        var schedule = JSON.parse(e.target.dataset.schedule);
        //在修改排片表单中的放映影厅的选择列表中，使显示出来的是它被排片时选择的影厅
        $("#schedule-edit-hall-input").children('option[value='+schedule.hallId+']').attr('selected',true);
        //在修改排片表单中的电影名称的选择列表中，使显示出来的是它被排片时选择的电影
        $("#schedule-edit-movie-input").children('option[value='+schedule.movieId+']').attr('selected',true);
        $("#schedule-edit-start-date-input").val(schedule.startTime.slice(0,16));
        $("#schedule-edit-end-date-input").val(schedule.endTime.slice(0,16));
        $("#schedule-edit-price-input").val(schedule.fare);
        //显示模态框
        $('#scheduleEditModal').modal('show');
        //更新data-schedule-id
        $('#scheduleEditModal')[0].dataset.scheduleId = schedule.id;
        //下面的方法用于在控制台输出信息
        console.log(schedule);
    });

    $('#schedule-form-btn').click(function () {//这个是新增排片的
    //#schedule-form-btn是新增排片的确认按钮
        //获取用户的输入
        var form = {
            hallId: $("#schedule-hall-input").children('option:selected').val(),
            movieId : $("#schedule-movie-input").children('option:selected').val(),//选中的电影的ID
            startTime: $("#schedule-start-date-input").val(),
            endTime: $("#schedule-end-date-input").val(),
            fare: $("#schedule-price-input").val()
        };
        //todo 需要做一下表单验证？
        //表单验证不通过就不提交表单
        if(!validateScheduleForm(form)){
            return;
        }

        postRequest(//向指定资源提交要处理的数据
            '/schedule/add',//URL
            form,//请求发送的数据
            function (res) {//请求成功后执行的函数
                if(res.success){
                    getSchedules();
                    $("#scheduleModal").modal('hide');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });


    $('#schedule-edit-form-btn').click(function () {
    //是修改排片的确认按钮
        var form = {
            id: Number($('#scheduleEditModal')[0].dataset.scheduleId),
            hallId: $("#schedule-edit-hall-input").children('option:selected').val(),
            movieId : $("#schedule-edit-movie-input").children('option:selected').val(),
            startTime: $("#schedule-edit-start-date-input").val(),
            endTime: $("#schedule-edit-end-date-input").val(),
            fare: $("#schedule-edit-price-input").val()
        };
        //todo 需要做一下表单验证？
        //表单验证不通过就不提交表单
        if(!validateScheduleEditForm(form)){
             return;
        }

        postRequest(
            '/schedule/update',
            form,
            function (res) {
                if(res.success){
                    getSchedules();
                    $("#scheduleEditModal").modal('hide');
                } else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });


    $("#schedule-edit-remove-btn").click(function () {
        var r=confirm("确认要删除该排片信息吗")
        if (r) {
            deleteRequest(
                '/schedule/delete/batch',
                {scheduleIdList:[Number($('#scheduleEditModal')[0].dataset.scheduleId)]},
                function (res) {
                    if(res.success){
                        getSchedules();
                        $("#scheduleEditModal").modal('hide');
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

    //表单验证：检查用户输入是否符合指定格式，别忘了在html文件中也要加元素！！！
    //新建排片的表单验证
    function validateScheduleForm(data){
            var isValidate=true;
            if(!data.hallId){
                isValidate=false;
                //showErrorMessage()
            }
            if(!data.movieId){
                isValidate=false;
                //showErrorMessage()
            }
            if(!data.startTime||!data.endTime){
                isValidate=false;
                //showErrorMessage()
            }
            function isAValidNum(f){
                var isValid=false;
                if(/^[0-9]+$/.test(f)||/^[0-9]+\.?[0-9]+?$/.test(f)){
                    isValid=true;
                }
                return isValid;
            }
            if(!data.fare||data.fare<0||!isAValidNum(data.fare)){
                //不存在或者为负数或者(不是整数且不是小数)
                isValidate=false;
                $('#schedule-price-error').css("visibility", "visible");
                $("#schedule-price-error").text("请正确输入数字");
                //showErrorMessage()
            }

            return isValidate;
    }

    //修改排片的表单验证
    function validateScheduleEditForm(data){
         var isValidate=true;
         if(!data.hallId){
              isValidate=false;
              //showErrorMessage()
         }
         if(!data.movieId){
              isValidate=false;
              //showErrorMessage()
         }
         if(!data.startTime||!data.endTime){
              isValidate=false;
              //showErrorMessage()
         }
         function isAValidNum(f){
              var isValid=false;
              if(/^[0-9]+$/.test(f)||/^[0-9]+\.?[0-9]+?$/.test(f)){
                   isValid=true;
              }
              return isValid;
         }
         if(!data.fare||data.fare<0||!isAValidNum(data.fare)){
               //不存在或者为负数或者(不是整数且不是小数)
               isValidate=false;
               $('#schedule-edit-price-error').css("visibility", "visible");
               $("#schedule-edit-price-error").text("请正确输入数字");
               //showErrorMessage()
         }

         return isValidate;
    }

});


