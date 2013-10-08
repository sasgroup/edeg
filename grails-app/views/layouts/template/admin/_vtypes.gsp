<!-- LIST -->
<script type="text/template" class="template" id="vtypes-list-template">
<div class="container">
    <div class="row">  
	<h3>List of Values Types</h3>
    <div id="input_form"></div>
 		<table id="table_items" class="table table-striped" >
			<thead>
                  <tr>                     
                     <th>Name</th>
                     <th>Description</th>            
                	 <th class="f-btn">Edit</th>
                	 <th class="f-btn">Delete</th>
                  </tr>
             </thead>
             <tbody>
            </tbody>                    
       </table>           
	</div>
</div>
</script>

<!-- SINGLE -->
<script type="text/template" class="template" id="single-vtype">
 <td><div class='name' title="{{name}}">{{ name }}</div></td>
 <td><div class='notes' title="{{description}}">{{ description }}</div></td> 
 <td class="f-btn"><div id="edit" class= "btn"><i class="icon-edit"></i></div></td>
 <td class="f-btn"><div id="destroy" class= "btn"><i class="icon-trash"></i></div></td> 
</script>

<!-- New/Edit ValuesType -->
<script type="text/template" class="template" id="vtype-template">
<form method="post" class="form-horizontal" id="vtype-edit" accept-charset="utf-8">
<table class="table" id="fields">
                      <tbody>
                          <tr>
                            <td>Name</td>
                            <td><input name="name" type="text"  id="name" maxlength="255"></td>                                                   
                                                
                            <td class="pull-right">Description</td>
                            <td>
                                <input name="description" type="text"  id="description" maxlength="255">
                            </td>
							<td><button type="submit" class="btn pull-right">Add New</button></td>               
                         </tr>                                    
                     </tbody>
</table> 
</form>
</script>
