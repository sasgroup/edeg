<!-- user-hospital-measure_table -->
<script type="text/template" class="template" id="user-hospital-measure_table">
<table class="hospitalMeasureTable table table-striped table-condensed" id="hosp-user-measure-table">
	<thead><tr>
		    <th>Use</th>
			<th>Category</th>		
			<th>ID</th>
		    <th>Name</th>				    				  
		    <th>Complete</th>		    
		    <th>Accepted</th>
			<th>Needs Review</th>
		    <th>Verified</th>
			<th>Help</th>		    
		   </tr>
    </thead>				
    <tbody>		
    </tbody>
</table>
</script>

<!-- user hospital_measure -->
<script type="text/template" class="template" id="user-hospital_measure">
 <td>
	<input type="checkbox" name="included" id="{{id}}" disabled="disabled" {{included}} {{included? "": "title='Contact IHM customer support to change selected measures'"}}>
 </td>
 <td><div>{{ measureCategory }}</div></td>
 <td><div class='code' title="">{{ code }}</div></td> 
 <td><div class='title' title="{{name}}"><a {{included? "id='customLink' href='#modalDataElements'": "class='disable'"}} role="button" data-toggle="modal">{{ name }}</a></div></td>
 <td>
    <input type="checkbox" name="completed" id="{{id}}" {{completed}} {{included? "": 'disabled="disabled"'}}>
 </td>
 <td>
     <input type="checkbox" name="accepted" id="{{id}}" disabled="disabled" {{accepted}}>
 </td>
 <td>
     <input type="checkbox" name="confirmed" id="{{id}}" disabled="disabled" {{confirmed}}>
 </td>
 <td>
     <input type="checkbox" name="verified" id="{{id}}" disabled="disabled" {{verified}}>
 </td>
 <td>
     <a class="btn show_info {{included? '': 'disabled'}}" rel="tooltip" mid="{{id}}" title="" {{included? "": 'disabled="disabled"'}}><i class="icon-info-sign" mid="{{id}}"></i></a>
 </td>
</script>

<script type="text/template" class="template" id="user-hospital-breadcrumb">
<ul class="breadcrumb">
	<li><a href="#home/{{h_id}}">Home</a><span class="divider">></span></li>	
    <li class="active">{{ product_code }}:</li>
	<li class="active">Measures</li>

	<li class="pull-right"><a {{notifyUser? 'class="btn btn-info edit-notes"': 'class="btn edit-notes"'}} rel="tooltip" title=""><i class="icon-edit"></i></a></li>
    <li class="pull-right"><a class="btn show-help" rel="tooltip" title=""><i class="icon-info-sign"></i></a></li>
	<li class="pull-right"><a class="btn show-notes" rel="tooltip" title=""><i class="icon-comment"></i></a></li>	
</ul>
</script>


<script type="text/template" class="template" id="edit-notes-temp">
  <div rows="3" class="txt-qa input-xlarge" placeholder="Click on the Data Element to view its details"><p>{{qa}}</p></div>					
  <textarea rows="1" class="txt-message input-xlarge" maxlength="4900"></textarea><td class="f-btn"><a class= "btn send-btn" title="Add"><i class="icon-share"></i></a> 
</script>