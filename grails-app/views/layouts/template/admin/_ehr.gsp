<!-- LIST -->
<script type="text/template" class="template" id="ehr-list-template">
<div class="container" >
    <div class="row">      
			<div class="row">
				<div class="span10">
					<h3>List of EHRs</h3>
				</div>				
				<div class="span2">
					<a class= "add-btn btn" id="create_ehr"  href="javascript:;">Add New</a>
				</div>
			</div>            
            <div class="clearfix"></div>
            <form action="billing" method="post" class="form-horizontal" id="billingform" accept-charset="utf-8">
            <table id="table_items" class="table table-striped" >
                <thead>
                    <tr>
                        <th>
                            ID
                        </th>
                        <th>
                            Name and Version
                        </th>
                        <th>
                            Description
                        </th>
                        <th class="f-btn">Edit</th>
            			<th class="f-btn">Delete</th>
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
<script type="text/template" class="template" id="ehr-template">
<div class="container">
    <div class="row">       
            <h3>{{state}}</h3>            
            <form method="post" class="form-horizontal" id="ehr-edit" accept-charset="utf-8">                
			<table class="table" id="fields">
              <tbody>
                <tr>
                  <td>ID<span class="required">*</span></td>
                  <td><input name="code" type="text" value="{{ code }}" id="code" maxlength="250"></td>
                  <td rowspan="2">
                    Description
                  </td>
                  <td rowspan="2" colspan="2">
                    <textarea rows="3" name="notes" id="notes" maxlength="1000">{{ notes }}</textarea>                  
                  </td>                            
                </tr>      
                <tr>
                  <td>Name and Version<span class="required">*</span></td>
                    <td>
                      <input name="name" type="text" value="{{ name }}" id="name" maxlength="250">
                    </td>                             
                </tr>                                    
              </tbody>
            </table>  
				
                <ul id="myTab" class="nav nav-tabs">                  
                  <li class="active">
                    <a data-toggle="tab" href="#elements">Default Data Element Locations</a>
                  </li>
			      <li class="">
                    <a data-toggle="tab" href="#hospitals">Hospitals</a>
                  </li>
                </ul>
     
                <div id="myTabContent" class="tab-content">
				   <div id="elements" class="tab-pane fade active in"></div>
                   <div id="hospitals" class="tab-pane fade "></div>			   				
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-info pull-right">Save EHR</button>
					<button type="reset" id="cancel" class="btn btn-info pull-right">Cancel</button>
                </div>
            </form>
    </div>
</div>
</script>


<!-- SINGLE -->
<script type="text/template" class="template" id="single-ehr">
 <td>{{ code }}</td>
 <td>{{ name }}</td>
 <td><div class='notes' title="{{notes}}">{{ notes }}</div></td> 
 <td class="f-btn"><div id="edit" class= "btn"><i class="icon-edit"></i></div></td>
 <td class="f-btn"><div id="destroy" class= "btn"><i class="icon-trash"></i></div></td>
</script>

<script type="text/template" class="template" id="single-ehr-hospital">
    <label class="Hospitals">
         {{name}}
    </label>
</script>

