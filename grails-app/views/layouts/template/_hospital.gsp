<!-- LIST -->
<script type="text/template" class="template" id="hospital-list-template">
<div class="container" >
<div class="row">
    <div class="span8">
        <h3>List of Hospitals</h3>
		<section class="row-fluid">
  			<a id="create_hospital">Create New</a>
		</section>
        <hr>
        <div class="clearfix"></div>
        <form method="post" class="form-horizontal" accept-charset="utf-8">

<table id="table_items" class="table table-striped" >
    <thead>
        <tr>
            <th>
                Name
            </th>
            <th>
                Notes
            </th>
           <th>Edit</th>
			<th>Delete</th>
        </tr>
    </thead>

    <tbody>    
    </tbody>
</table>

        </form>
    </div> 
</div>
</div>
</script>

<!-- NEW/EDIT -->
<script type="text/template" class="template" id="hospital-template">
<div class="container">
    <div class="row">
        <div>
            <h3>Hospital: {{state}}</h3>
            <hr>
            <div class="clearfix"></div>
            <form method="post" class="form-horizontal" id="hospital-edit" accept-charset="utf-8">                
                <div class="control-group">
                    <label for="name" class="control-label">
                        Name
                    </label>
                    <div class="controls">
                        <input name="name" type="text" value="{{ name }}" id="name">
                    </div>
                </div>
				<div class="control-group">
                    <label for="notes" class="control-label">
                        Notes
                    </label>
                    <div class="controls">
                        <textarea rows="3" name="notes" id="notes"></textarea>
                    </div>
                </div>
				
				<div class="control-group">
					<label for="notes" class="control-label">
                        Products
                    </label>
	 				<div class="controls">
					  <select id="example" name="example" multiple="multiple">
					    <option value="1">Option 1</option>
					    <option value="2">Option 2</option>
					    <option value="3">Option 3</option>
					    <option value="4">Option 4</option>
					    <option value="5">Option 5</option>
					  </select>
 					</div>
				</div>
                
                <ul id="myTab" class="nav nav-tabs">
                  <li class="active">
                    <a data-toggle="tab" href="#product1">Product1</a>
                  </li>
                  <li class="">
                    <a data-toggle="tab" href="#product2">Product2</a>
                  </li>
                </ul>
     
                <div id="myTabContent" class="tab-content">
                   <div id="product1" class="tab-pane fade active in">				     
                   </div>
                   <div id="product2" class="tab-pane fade">              		
                   </div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-info">Save Hospital</button>
                </div>
            </form>
        </div>
    </div>
</div>
</script>


<!-- SINGLE -->
<script type="text/template" class="template" id="single-hospital">
 <td>{{ name }}</td>
 <td>{{ notes }}</td>
 <td><div id="edit" class= "btn"><i class="icon-edit"></i></div></td>
 <td><div id="destroy" class= "btn"><i class="icon-trash"></i></div></td> 
</script>
