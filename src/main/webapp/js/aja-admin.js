$.ajaxSetup({
	statusCode : {
		401 : function() {
			alert("A rendszer kijelentkeztette, jelentkezzen be újra!");
			location.reload();
		}
	}
});

function clearDataTable(selector){
	element = $(selector)
	
	if ($.fn.DataTable.isDataTable(element)){
		$(element).DataTable().clear().draw();
	}
}

function initActionIdAjax(selector){
	element = $(selector)
	element.select2({
		placeholder: 'Válasszon akciókódokat',
		allowClear: true,
		ajax: {
			url: 'action/ids',
			dataType: 'json',
			delay: 250,
			data: function(data){
				return {
					action_id_like : data.term ? data.term.trim(): null
				};
		},
		processResults : function(data) {
			return {
				results : $.map(data, function(item) {
					return {
						id : item.AZONOSITO,
						text : item.AZONOSITO
					};
				})
			};
		},
		cache : true
	}
	});
}

function initPartnerIdAjax(selector){
	element = $(selector)
	$(element).select2({
		placeholder: 'Válasszon partnert',
		allowClear: true,
		ajax: {
			url: 'partner/',
			dataType: 'json',
			delay: 250,
			data: function(data){
				return {
					partner_id : data.term ? data.term.trim(): null,
					name_like : data.term ? data.term.trim(): null
				};
		},
		processResults : function(data) {
			return {
				results : $.map(data, function(item) {
					return {
						id : item.AZONOSITO,
						text : `${item.NEV} (${item.AZONOSITO})`
					};
				})
			};
		},
		cache : true
	}
});

}