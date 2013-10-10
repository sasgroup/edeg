<!-- FORM TEMPLATE -->
<script type="text/template" class="template" id="vtype-template">
<div class="container">
  <div class="row">  
    <h3>List of Values Types</h3>
    <div id="input_form"></div>
    <div id="list_form"></div>
  </div>
</div>
</script>

<!-- LIST -->
<script type="text/template" class="template" id="vtypes-list-template">
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
</script>

<!-- SINGLE -->
<script type="text/template" class="template" id="single-vtype">
 <td><div class='name'>{{ name }}</div></td>
 <td><div class='notes'>{{ description }}</div></td> 
 <td class="f-btn"><div id="edit" class= "btn"><i class="icon-edit"></i></div></td>
 <td class="f-btn"><div id="destroy" class= "btn"><i class="icon-trash"></i></div></td> 
</script>

<!-- New/Edit ValuesType -->
<script type="text/template" class="template" id="vtype-edit">
<form method="post" class="form-horizontal" id="form-vtype-edit" accept-charset="utf-8">
  <input type="hidden" name="id" value="">
    <table class="table" id="fields">
      <tbody>
        <tr>							
          <td>Name</td>
          <td><input name="name" type="text"  id="name" maxlength="255" value="{{ name }}"></td>                                       
          <td class="pull-right">Description</td>
          <td><input name="description" type="text" id="description" maxlength="1000" value="{{ description }}"></td>
		  <td><button type="submit" class="btn pull-right">Save/Update</button></td>           
        </tr>                                    
      </tbody>
    </table> 
</form>    
</script>
