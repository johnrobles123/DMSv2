$('#iconified').on('keyup', function() {
    var input = $(this);
    if(input.val().length === 0) {
        input.addClass('empty');
    } else {
        input.removeClass('empty');
    }
});

function Pager(tableName, itemsPerPage) {
	this.tableName = tableName;
	this.itemsPerPage = itemsPerPage;
	this.currentPage = 1;
	this.pages = 0;
	this.inited = false;
	
	this.showRecords = function(from, to) {
	var rows = document.getElementById(tableName).rows;
	// i starts from 1 to skip table header row
	for (var i = 1; i < rows.length; i++) {
		if (i < from || i > to)
			rows[i].style.display = 'none';
		else
			rows[i].style.display = '';
		}
	}
	this.showPage = function(pageNumber) {
		if (! this.inited) {
			alert("not inited");
			return;
		}
	
		var oldPageAnchor = document.getElementById('pg'+this.currentPage);
		oldPageAnchor.className = 'pg-normal';
	
		this.currentPage = pageNumber;
	
                this.showPageNav('pager', 'pageNavPosition');    
    
		var newPageAnchor = document.getElementById('pg'+this.currentPage);
		newPageAnchor.className = 'pg-selected paginationButtonSelected';
		
		var from = (pageNumber - 1) * itemsPerPage + 1;
	
		var to = from + itemsPerPage - 1;
	
		this.showRecords(from, to);

	}

	this.prev = function() {

		if (this.currentPage > 1)

			this.showPage(this.currentPage - 1);

	}

	this.next = function() {

		if (this.currentPage < this.pages) {

			this.showPage(this.currentPage + 1);

		}

	}

	this.init = function() {

		var rows = document.getElementById(tableName).rows;
		var records = (rows.length - 1);

		this.pages = Math.ceil(records / itemsPerPage);
		this.inited = true;
	}

	this.showPageNav = function(pagerName, positionId) {

		var pageIndex = this.currentPage,
		pageCount = this.pages,
		start = Math.max(1, pageIndex - 4),
	    end = Math.min(pageCount, pageIndex + 4),
	    start2 = pageCount-4,
	    end2 = pageCount;
	    
	    if (start2 <= end)
	        end = pageCount;
    
	    if (! this.inited) {

	    	alert("not inited");

	    	return;

	    }

	var element = document.getElementById(positionId);
	var pagerHtml = '';
    
	// adds the Prev button only if needed
	if (pageIndex > 1)
	    pagerHtml = '<span onclick="' + pagerName + '.prev();" class="pg-normal paginationButton" id="iconified"> &#xf060 Prev </span> ';
	
	    // paging from 1 to 5   
	    for (var page = start; page <= end; page++) {
	
	    	pagerHtml += '<span id="pg' + page + '" class="pg-normal paginationButton" onclick="' + pagerName + '.showPage(' + page + ');">' + page + '</span> ';
	    }
    
    // paging from pageCount-5 to pageCount
    if (end != pageCount) {
         pagerHtml += '<span>...</span>';
        
        for (var page = start2; page <= end2; page++) {
    
        	pagerHtml += '<span id="pg' + page + '" class="pg-normal paginationButton" onclick="' + pagerName + '.showPage(' + page + ');">' + page + '</span> ';
        }   
    }
	// adds the Next button only if needed
	if (pageIndex < pageCount && pageCount > 1)    
	    pagerHtml += '<span onclick="'+pagerName+'.next();" class="pg-normal paginationButton" id="iconified"> Next &#xf061</span>';
	
	element.innerHTML = pagerHtml;
	
	}
}

function Pager2(tableName2, itemsPerPage2) {
	this.tableName2 = tableName2;
	this.itemsPerPage2 = itemsPerPage2;
	this.currentPage2 = 1;
	this.pages = 0;
	this.inited = false;
	
	this.showRecords2 = function(from2, to2) {
	var rows2 = document.getElementById(tableName2).rows;
	// i starts from2 1 to2 skip table header row
	for (var i = 1; i < rows2.length; i++) {
		if (i < from2 || i > to2)
			rows2[i].style.display = 'none';
		else
			rows2[i].style.display = '';
		}
	}
	this.showPage2 = function(pageNumber) {
		if (! this.inited) {
			alert("not inited");
			return;
		}
	
		var oldPageAnchor2 = document.getElementById('pg2'+this.currentPage2);
		oldPageAnchor2.className = 'pg2-normal';
	
		this.currentPage2 = pageNumber;
	
                this.showPageNav2('pager2', 'pageNavPosition2');    
    
		var newPageAnchor2 = document.getElementById('pg2'+this.currentPage2);
		newPageAnchor2.className = 'pg2-selected paginationButtonSelected';
		
		var from2 = (pageNumber - 1) * itemsPerPage2 + 1;
	
		var to2 = from2 + itemsPerPage2 - 1;
	
		this.showRecords2(from2, to2);

	}

	this.prev = function() {

		if (this.currentPage2 > 1)

			this.showPage2(this.currentPage2 - 1);

	}

	this.next = function() {

		if (this.currentPage2 < this.pages) {

			this.showPage2(this.currentPage2 + 1);

		}

	}

	this.init = function() {

		var rows2 = document.getElementById(tableName2).rows;
		var records2 = (rows2.length - 1);

		this.pages = Math.ceil(records2 / itemsPerPage2);
		this.inited = true;
	}

	this.showPageNav2 = function(pagerName2, positionId2) {

		var pageIndex2 = this.currentPage2,
		pageCount2 = this.pages,
		starting = Math.max(1, pageIndex2 - 4),
	    ending = Math.min(pageCount2, pageIndex2 + 4),
	    starting2 = pageCount2-4,
	    ending2 = pageCount2;
	    
	    if (starting2 <= ending)
	        ending = pageCount2;
    
	    if (! this.inited) {

	    	alert("not inited");

	    	return;

	    }

	var element2 = document.getElementById(positionId2);
	var pagerHtml2 = '';
    
	// adds the Prev button only if needed
	if (pageIndex2 > 1)
	    pagerHtml2 = '<span onclick="' + pagerName2 + '.prev();" class="pg2-normal paginationButton" id="iconified"> &#xf060 Prev </span> ';
	
	    // paging from2 1 to2 5   
	    for (var page2 = starting; page2 <= ending; page2++) {
	
	    	pagerHtml2 += '<span id="pg2' + page2 + '" class="pg2-normal paginationButton" onclick="' + pagerName2 + '.showPage2(' + page2 + ');">' + page2 + '</span> ';
	    }
    
    // paging from2 pageCount2-5 to2 pageCount2
    if (ending != pageCount2) {
         pagerHtml2 += '<span>...</span>';
        
        for (var page2 = starting2; page2 <= ending2; page2++) {
    
        	pagerHtml2 += '<span id="pg2' + page2 + '" class="pg2-normal paginationButton" onclick="' + pagerName2 + '.showPage2(' + page2 + ');">' + page2 + '</span> ';
        }   
    }
	// adds the Next button only if needed
	if (pageIndex2 < pageCount2 && pageCount2 > 1)    
	    pagerHtml2 += '<span onclick="'+pagerName2+'.next();" class="pg2-normal paginationButton" id="iconified"> Next &#xf061</span>';
	
	element2.innerHTML = pagerHtml2;
	
	}

}


function ddListDeviceOnChange() {
    var x = document.getElementById("ddDeviceList").value;
    var url = "/DMSv2/dashboard/" + x + "/refresh"; // get selected value
    if (url) { // require a URL
    	location.href = url; // redirect
    }
    return false;
 }

function checkBoxValidation() {
	var displayReturn = "false";			
	$('#devicejournaltable').find('tr').each(function () {
        var row = $(this);
        if (row.find('input[type="checkbox"]').is(':checked')) {
        	displayReturn = "true";
        }
    }); 
    
    if (displayReturn == "true") {
    	document.getElementById("reserveLink").style.visibility = "hidden";
    	document.getElementById("reserveLink").style.float = "left";
    	document.getElementById("returnLink").style.visibility = "visible";
    	document.getElementById("returnLink").style.float = "right";
    	
    } else {
    	document.getElementById("reserveLink").style.visibility = "visible";
    	document.getElementById("reserveLink").style.float = "right";
    	document.getElementById("returnLink").style.visibility = "hidden";
    	document.getElementById("returnLink").style.float = "left";
    }
}

function processReturn() {		
	$('#devicejournaltable').find('tr').each(function () {
        var row = $(this);
        if (row.find('input[type="checkbox"]').is(':checked')) {
        	var seqNo = parseInt(row.find("#seqNo").html());
        	$.ajax({
                url : '/DMSv2/dashboard/' + seqNo + '/return',
                type: 'POST',
                data : {"${_csrf.parameterName}" : "${_csrf.token}"},
                success : function(data) {
                }
            });
        }
    });
}

/*
function processCancel(seqNo) {
	$("#confirmCancelDialogue").dialog({
		modal: true,
		title: "Confirmation",
		resizable: false,
		buttons: {
			"YES": function() {
	        	$.ajax({
	                url : '/DMSv2/dashboard/' + seqNo + '/cancel',
	                type: 'POST',
	                data : {"${_csrf.parameterName}" : "${_csrf.token}"},
	                success : function(data) {
	                }
	            });
	        	
				$(this).dialog("close");
				location.reload(true);
			},
			"NO": function() {
				$(this).dialog("close");
			}
		}
	});
}
*/

function deleteDevice(serialNo) {
	$.ajax({
        url : '/DMSv2/devicelist/' + serialNo + '/delete',
        type: 'POST',
        data : {"${_csrf.parameterName}" : "${_csrf.token}"},
        success : function(data) {
        }
    });

}

/*
function confirmDelete(serialNo) {
	$("#confirmDeleteDialogue").dialog({
	modal: true,
	title: "Confirmation",
	resizable: false,
	buttons: {
			"YES": function() {
				deleteDevice(serialNo);
				$(this).dialog("close");
				location.reload(true);
			},
			"NO": function() {
				$(this).dialog("close");
			}
		}
	});
}
*/