var init = false;
$(document).ready(function () {
    $('.btn-success').on('click', function (e) {
        searchReportData();
    });

    function searchReportData() {
        showOverlay();
        if (init === false) {
            $('#cucuReport').DataTable({
                ajax: {
                    url: "webresources/Services/getReportData?fromDate=" + $('#fromDate').val() + "&toDate=" + $('#toDate').val() + "&searchOption=" + $('#searchOption').val(),
                    dataSrc: "data"
                },
                columns: [
                    {data: 'entityName'},
                    {data: 'entityUCN'},
                    {data: 'entityRegNumber'},
                    {data: 'relatedPartyName'},
                    {data: 'relatedPartyUCN'},
                    {data: 'relatedPartyIDNumber'},
                    {data: 'cucu'},
                    {data: 'status'}                    
                ]
            });
            init=true;
        } else {
            $('#cucuReport').DataTable().ajax.reload();
            $('#cucuReport').DataTable().draw();
        }
        closeOverlay();
    }
});