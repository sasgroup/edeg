<script type="text/template" class="template" id="user-measure-breadcrumb">
<ul class="breadcrumb">
	<li><a href="#home/{{hospital_id}}">Home</a><span class="divider">></span></li>
	<li><a href="#hospital/{{hospital_id}}/product/{{ product_id }}">{{ product_code }}</a><span class="divider">></span></li>
	<li class="active">{{ measure_code }}:</li>
	<li class="active">Data Elements</li>

	<li class="pull-right"><a {{notifyUser? 'class="btn btn-info edit-notes"': 'class="btn edit-notes"'}} rel="tooltip" title=""><i class="icon-edit"></i></a></li>
	<li class="pull-right"><a class="btn show-help" rel="tooltip" title=""><i class="icon-info-sign"></i></a></li>
	<li class="pull-right"><a class="btn show-notes" rel="tooltip" title=""><i class="icon-comment"></i></a></li>	
</ul>
</script>