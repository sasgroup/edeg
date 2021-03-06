<!-- LIST -->
<script type="text/template" class="template" id="hospital-list-template">
<div class="container" >
<div class="row">    
        <h3>List of Hospitals</h3>        
        <div class="clearfix"></div>
        <form method="post" class="form-horizontal" id="hospital-list" accept-charset="utf-8">
			<table id="table_items" class="table table-striped" >
    			<thead>
        			<tr>
            			<th>
                			Name
            			</th>
            			<th>
                			IHM Notes
            			</th>
            			<th class="f-btn">Edit</th>			
        			</tr>
    			</thead>
    			<tbody>    
    			</tbody>
			</table>
        </form>
</div>
</div>
</script>

<!-- NEW/EDIT -->
<script type="text/template" class="template" id="hospital-template">

<div class="container">
   <div class="row">   
   <h3>Edit Hospital</h3>
   <form method="post" class="form-horizontal" id="hospital-edit" accept-charset="utf-8">    
	<table class="table table-condensed" id="fields">
        <tbody>
			  <tr>
                <td>Name</td>
                <td><input name="name" type="text" value="{{ name }}" id="name" disabled></td>
                <td rowspan="2">
					IHM Notes			
				</td>
                <td rowspan="2" colspan="2">
                	<textarea name="notes" rows="2" id="notes" maxlength="4000">{{ notes }}</textarea>					
                </td>				
             </tr>	
			 <tr>
                <td>Email</td>
                <td><input name="email" type="text"  maxlength="255" value="{{ email }}" id="email" placeholder="email@example.com"/></td>               
             </tr>		
			 
			<tr>
                <td>Primary EHR</td>
                <td>
                    <select id="slcEHRs">                      
                    </select>
                </td>
                <td>IHM Products</td>
                <td>
                    <select id="slcProducts">          
                    </select>											
                </td>    
				<td><button id="btnApplyHospitalOptions" class="btn btn-info pull-right">Apply</button></td>            
             </tr>
			
			<tr>
				<td title="Meaningful Use Population Method">Population Method</td>
				<td>
					<select id="slcPopulationMethod" name="populationMethod">
						<option value="ED-ALL" {{populationMethod=="ED-ALL"? "selected": ""}} >ED-ALL</option>
						<option value="ED-OBS" {{populationMethod=="ED-OBS"? "selected": ""}}>ED-OBS</option>
					</select>	
					<a id="btnExternalEHRs" href="javascript:;" >Additional EHRs <i class="icon-th-list"></i></a>				
				</td>	
				<td>Hospital</td>
				<td>
					<select id="slcHospitals">
						<option value=''>Select</option>						
					</select>
				</td>	
				<td>
					<button id="btnCloneHospital" class="btn btn-info pull-right">Clone from</button>
				</td>		
			</tr>	
        </tbody>
    </table>   
   
		<div id="divExternalEHRs" class="modal hide fade">
    		<div class="modal-header">
    			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    			<h3>Additional EHRs for Hospital [ {{ name }} ]</h3>
    		</div>
			
    		<div class="modal-body">
    			<textarea rows="8" name="externalEHRs" id="txtEHRs" class="helpArea helpAreaHospital">{{ externalEHRs }}</textarea>
    		</div>
		</div>

    <ul id="myTab" class="nav nav-tabs">               
    </ul>     
    <div id="myTabContent" class="tab-content">               
    </div>

	<div class="form-actions">
                    <a class="btn pull-left admin-edit-notes"><i class="icon-edit"></i>Client Product Notes</a>					
					<button type="submit" id="submit-close-btn" class="btn btn-info pull-right">Save&Close</button>
					<button type="submit" id="submit-btn" class="btn btn-info pull-right">Save Hospital</button>
					<button id="cancel" class="btn btn-info pull-right">Cancel</button>
					
    </div>
	
	 </form>  
  </div>
</div>
<div id="loading">
<img id="loading-image" src="/ihm/static/images/ajax_loader.gif" alt="Loading..." />
</div>

</script>

<!-- Product/EHR Option -->
<script type="text/template" class="template" id="product-option">
 <option value={{id}} name={{id}}>{{code}} </option>
</script>


<!-- SINGLE -->
<script type="text/template" class="template" id="single-hospital">
 <td><div class='name' title="{{name}}">{{ name }}</div></td>
 <td><div class='notes' title="{{notes}}">{{ notes }}</div></td>
 <td class="f-btn"><div id="edit" class= "btn"><i class="icon-edit"></i></div></td> 
</script>

<!-- hospital-measure_table -->
<script type="text/template" class="template" id="hospital-measure_table">
<table class="hospitalMeasureTable table table-striped ">
	<thead><tr>
		    <th>Use</th>
			<th>Category</th>			
			<th>ID</th>
		    <th>Name</th>				    				  
		    <th>Complete</th>		    
		    <th>Accepted</th>
			<th class="needs-rv">Needs Review</th>
		    <th>Verified</th>		    
		   </tr>
    </thead>				
    <tbody>		
    </tbody>
</table>
</script>

<!-- hospital_measure -->
<script type="text/template" class="template" id="single-hospital_measure">
 <td>
	<input type="checkbox" name="included" id="{{id}}" {{included}} >
 </td> 
 <td><div>{{ measureCategory }}</div></td>
 <td><div class='code'>{{ code }}</div></td>
 <td><div class='title' title="{{name}}"><a id="customLink" href='{{path}}' role="button" data-toggle="modal">{{ name }}</a></div></td>
 <td>
    <input type="checkbox" name="completed" id="{{id}}" {{completed}}>
 </td> 
 <td>
     <input type="checkbox" name="accepted" id="{{id}}" {{accepted}}>
 </td>
 <td>
     <input type="checkbox" name="confirmed" id="{{id}}" {{confirmed}}>
 </td>
 <td>
     <input type="checkbox" name="verified" id="{{id}}" {{verified}}>
 </td>
</script>