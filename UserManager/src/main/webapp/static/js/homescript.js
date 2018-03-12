$(document).ready(function () {
    var chart;
    $('.btn-start').on('click', function (e) {
        $.ajax({
            url: "webresources/Services/startWorker",
            type: 'GET',
            success: function (result) {
                closeOverlay();
            },
            error: function (jqXHR, exception) {
                closeOverlay();
                var msg = '';
                if (jqXHR.status === 404) {
                    msg = 'Requested page not found. [404]';
                } else if (jqXHR.status === 500) {
                    msg = 'Internal Server Error [500].';
                } else if (exception === 'parsererror') {
                    msg = 'Requested JSON parse failed.';
                } else if (exception === 'timeout') {
                    msg = 'Time out error.';
                } else if (exception === 'abort') {
                    msg = 'Ajax request aborted.';
                } else {
                    msg = 'Uncaught Error.\n' + jqXHR.responseText;
                }
            }
        });
    });

    $('.btn-stop').on('click', function (e) {
        $.ajax({
            url: "webresources/Services/stopWorker",
            type: 'GET',
            success: function (result) {
                closeOverlay();
            },
            error: function (jqXHR, exception) {
                closeOverlay();
                var msg = '';
                if (jqXHR.status === 404) {
                    msg = 'Requested page not found. [404]';
                } else if (jqXHR.status === 500) {
                    msg = 'Internal Server Error [500].';
                } else if (exception === 'parsererror') {
                    msg = 'Requested JSON parse failed.';
                } else if (exception === 'timeout') {
                    msg = 'Time out error.';
                } else if (exception === 'abort') {
                    msg = 'Ajax request aborted.';
                } else {
                    msg = 'Uncaught Error.\n' + jqXHR.responseText;
                }
            }
        });
    });
    
    
    $('.btn-pause').on('click', function (e) {
        $.ajax({
            url: "webresources/Services/pauseWorker",
            type: 'GET',
            success: function (result) {
                closeOverlay();
            },
            error: function (jqXHR, exception) {
                closeOverlay();
                var msg = '';
                if (jqXHR.status === 404) {
                    msg = 'Requested page not found. [404]';
                } else if (jqXHR.status === 500) {
                    msg = 'Internal Server Error [500].';
                } else if (exception === 'parsererror') {
                    msg = 'Requested JSON parse failed.';
                } else if (exception === 'timeout') {
                    msg = 'Time out error.';
                } else if (exception === 'abort') {
                    msg = 'Ajax request aborted.';
                } else {
                    msg = 'Uncaught Error.\n' + jqXHR.responseText;
                }
            }
        });
    });
    

    $(document).ready(function () {
        $.ajax({
            url: "webresources/Services/getWorkerStatus",
            type: 'GET',
            success: function (data) {
                if($('.btn-status').show()){
                    changeButtonForStatus(data); 
                }
                document.getElementById("status").innerHTML = data;
                closeOverlay();
            },
            error: function (jqXHR, exception) {
                closeOverlay();
                var msg = '';
                if (jqXHR.status === 404) {
                    msg = 'Requested page not found. [404]';
                } else if (jqXHR.status === 500) {
                    msg = 'Internal Server Error [500].';
                } else if (exception === 'parsererror') {
                    msg = 'Requested JSON parse failed here.';
                } else if (exception === 'timeout') {
                    msg = 'Time out error.';
                } else if (exception === 'abort') {
                    msg = 'Ajax request aborted.';
                } else {
                    msg = 'Uncaught Error.\n' + jqXHR.responseText;
                }

            }});
        chart = Highcharts.chart({
            chart: {
                renderTo: 'mychart',
                events: {
                    load: requestData
                }
            },
            title: {
                text: 'Batch Processing Status'
            },
            credits: {text: ""},
            xAxis: {
                categories: ['Totals', 'Failed', 'Not done', 'Succesful']
            },
            labels: {
                items: [{
                        html: 'Total Batch Status',
                        style: {
                            left: '50px',
                            top: '18px',
                            color: (Highcharts.theme && Highcharts.theme.textColor) || 'black'
                        }
                    }]
            },
            series: [{
                    type: 'column',
                    name: 'Exceptions',
                    data: [3]
                }, {
                    type: 'column',
                    name: 'Failed',
                    data: [5]
                }, {
                    type: 'column',
                    name: 'Not done',
                    data: [4]
                }, {
                    type: 'column',
                    name: 'Succesful',
                    data: [2]
                }, {
                    type: 'pie',
                    name: 'Batch Status',
                    data: [{
                            name: 'Exceptions',
                            y: 13,
                            color: Highcharts.getOptions().colors[0]
                        }, {
                            name: 'Failed',
                            y: 23,
                            color: Highcharts.getOptions().colors[1]
                        }, {
                            name: 'Not done',
                            y: 19,
                            color: Highcharts.getOptions().colors[2]
                        }, {
                            name: 'Successful',
                            y: 19,
                            color: Highcharts.getOptions().colors[3]
                        }],
                    center: [100, 80],
                    size: 100,
                    showInLegend: false,
                    dataLabels: {
                        enabled: false
                    }
                }]
        });
    });

    //Add javascript timer here
    (function poll() {
        setTimeout(function () {
            $.ajax({
                url: "webresources/Services/getWorkerStatus",
                type: 'GET',
                success: function (data) {
                    if($('.btn-status').show()){
                        changeButtonForStatus(data); 
                    }
                    document.getElementById("status").innerHTML = data;
                    closeOverlay();
                },
                error: function (jqXHR, exception) {
                    closeOverlay();
                    var msg = '';
                    if (jqXHR.status === 404) {
                        msg = 'Requested page not found. [404]';
                    } else if (jqXHR.status === 500) {
                        msg = 'Internal Server Error [500].';
                    } else if (exception === 'parsererror') {
                        msg = 'Requested JSON parse failed here.';
                    } else if (exception === 'timeout') {
                        msg = 'Time out error.';
                    } else if (exception === 'abort') {
                        msg = 'Ajax request aborted.';
                    } else {
                        msg = 'Uncaught Error.\n' + jqXHR.responseText;
                    }

                }, complete: poll});
        }, 2000);
    })();

    function requestData() {
        $.ajax({
            url: 'webresources/Services/getChartData',
            success: function (data) {
                var exceptions = data['Exceptions'];
                var total_failed = data['Failed'];
                var notdone = data['Not done'];
                var success = data['Successful'];
                chart.series[0].update({data: [exceptions]}, true);
                chart.series[1].update({data: [total_failed]}, true);
                chart.series[2].update({data: [notdone]}, true);
                chart.series[3].update({data: [success]}, true);
                chart.series[4].update({data: [{
                            y: exceptions
                        }, {
                            y: total_failed
                        }, {
                            y: notdone
                        }, {
                            y: success
                        }]
                });
                setTimeout(requestData, 2000);
            },
            cache: false
        });
    }
    
    //Function to change the color of the button for the status
    function changeButtonForStatus(data){
        switch(data) {
            case "Shutting down":
                $('.btn-status').css('background-color', '#fa9d1e');
                break;
            case "Running":
                $('.btn-status').css('background-color', '#00595a');
                break;
            case "Shut down":
                $('.btn-status').css('background-color', '#f47920');
                break;
            case "Paused":
                $('.btn-status').css('background-color', '#000000');
                break;
            default:
                $('.btn-status').css('background-color', '#00595a');
        }
    }
});