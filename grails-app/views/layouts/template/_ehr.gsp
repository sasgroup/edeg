<!-- LIST -->
<script type="text/template" class="template" id="ehr-list-template">
<div class="container" >
    <div class="row">       
            <h3>List of EHR</h3>
    		<section class="row-fluid">
      			<a id="create_ehr">Create New</a>
    		</section>
            <hr>
            <div class="clearfix"></div>
            <form action="billing" method="post" class="form-horizontal" id="billingform" accept-charset="utf-8">
            <table id="table_items" class="table table-striped" >
                <thead>
                    <tr>
                        <th>
                            Code
                        </th>
                        <th>
                            Name
                        </th>
                        <th>
                            Notes
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
            <h3>EHR: {{state}}</h3>
            <hr>
            <div class="clearfix"></div>
            <form method="post" class="form-horizontal" id="ehr-edit" accept-charset="utf-8">
                <div class="container">
  				    <div class="row">
    				  <div class="span5">      				  
                        <div class="row-fluid">
                          <label for="code" class="span3">Code</label>    
					      <input name="code" type="text" class="span9" value="{{ code }}" id="code">
					    </div>					  
					    <div class="row-fluid">	
                          <label for="name" class="span3">Name</label>                                         
                          <input name="name" type="text" class="span9" value="{{ name }}" id="name">
					   </div> 					
					  </div>    				  
					  <div class="span7">
					    <div class="row-fluid">	
      				      <label for="notes" class="span2">Notes</label>                    
                          <textarea rows="3" class="span10" name="notes" id="notes">{{ notes }}</textarea>
    				    </div>
					  </div>
  				    </div>
				</div>
				<hr>

                <ul id="myTab" class="nav nav-tabs">
                  <li class="active">
                    <a data-toggle="tab" href="#hospitals">Hospital</a>
                  </li>
                  <li class="">
                    <a data-toggle="tab" href="#elements">DataElementsDefault</a>
                  </li>
                </ul>
     
                <div id="myTabContent" class="tab-content">
                   <div id="hospitals" class="tab-pane fade active in">    
                   </div>					
				   <div id="elements" class="tab-pane fade">						        
                   </div>				
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-info pull-right">Save EHR</button>
                </div>
            </form>
    </div>
</div>
</script>


<!-- SINGLE -->
<script type="text/template" class="template" id="single-ehr">
 <td>{{ code }}</td>
 <td>{{ name }}</td>
 <td>{{ notes }}</td> 
 <td class="f-btn"><div id="edit" class= "btn"><i class="icon-edit"></i></div></td>
 <td class="f-btn"><div id="destroy" class= "btn"><i class="icon-trash"></i></div></td>
</script>

<script type="text/template" class="template" id="single-ehr-hospital">
    <label class="Hospitals">
         {{name}}
    </label>
</script>

