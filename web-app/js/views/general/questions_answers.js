//QA for DataElement 
App.Views.QADataElement = Backbone.View
.extend({ 
  template: _.template($('#qa').html()),      
  
  events : {
    'click .send-btn' : 'appendQuestion',
    'keypress .txt-message': 'sendOnEnter'
  },
          
  render : function() {
    //console.log(this.model.toJSON());        
        
    if (this.options.tab=="tab-qa2") {
      this.$el.html(this.template({notes:this.model.get('notes')}));    
    } else 
    if (this.options.tab=="tab-qa3") {
      this.$el.html(this.template({notes:this.model.get('internalNotes')}));    
    }
    
    this.$el.attr('id',this.options.tab);    
    
    return this;
  },
  
  sendOnEnter: function(e) {
      if (e.keyCode == 13) {
    	  this.appendQuestion();
      }
      this.$el.find('.txt-qa').scrollTop(this.$el.find('.txt-qa')[0].scrollHeight);
   },
  
  appendQuestion : function() {
    var message = this.$el.find(".txt-message").val();
    
    if (message.length!=0) {
    
    var txt =  this.$el.find(".txt-qa").html();
    
    var date = new Date();        
    var hours = date.getHours();
    var minutes = date.getMinutes();
    var ampm = hours >= 12 ? 'pm' : 'am';
    hours = hours % 12;
    hours = hours ? hours : 12; // the hour '0' should be '12'
    minutes = minutes < 10 ? '0'+minutes : minutes;
    var strTime = hours + ':' + minutes + ' ' + ampm;
    
    var messageTimeStamp = (date.getMonth() + 1) + "/" + date.getDate() + "/"  + date.getFullYear().toString() + " " +strTime;
    
    var user = App.userRole;
    
    message = user + ", " + messageTimeStamp + ": " + message;
    txt = txt + "<p>" + message + "</p>";
        
    this.$el.find(".txt-qa").html(txt);
    this.$el.find(".txt-message").val('');    
        
    if (this.options.tab=="tab-qa2") {
      this.model.set({"notes":txt});
    } else 
    if (this.options.tab=="tab-qa3") {
      this.model.set({"internalNotes":txt});
    }
  }
  }
});

//QA for Product/Measure
App.Views.QA = Backbone.View
.extend({ 
  template: _.template($('#edit-notes-temp').html()),      
  
  events : {
    'click .send-btn' 	    : 'appendQuestion',
    'keypress .txt-message' : 'sendOnEnter'
  },
          
  render : function() {
    //console.log(this.model.toJSON());        
        
    this.$el.html(this.template({qa:this.model.get('qa')}));    
    
    
    return this;
  },
  
  sendOnEnter: function(e) {
      if (e.keyCode == 13) {
    	  this.appendQuestion();
      }
      this.$el.find('.txt-qa').scrollTop(this.$el.find('.txt-qa')[0].scrollHeight);
   },
  
  appendQuestion : function() {
    var message = this.$el.find(".txt-message").val();
    
    if (message.length!=0) {
    
    var txt =  this.$el.find(".txt-qa").html();
    
    var date = new Date();        
    var hours = date.getHours();
    var minutes = date.getMinutes();
    var ampm = hours >= 12 ? 'pm' : 'am';
    hours = hours % 12;
    hours = hours ? hours : 12; // the hour '0' should be '12'
    minutes = minutes < 10 ? '0'+minutes : minutes;
    var strTime = hours + ':' + minutes + ' ' + ampm;
    
    var messageTimeStamp = (date.getMonth() + 1) + "/" + date.getDate() + "/"  + date.getFullYear().toString() + " " +strTime;
    
    var user = App.userRole;
    
    message = user + ", " + messageTimeStamp + ": " + message;
    txt = txt + "<p>" + message + "</p>";
        
    this.$el.find(".txt-qa").html(txt);
    this.$el.find(".txt-message").val('');    
        
    this.model.set({"qa":txt});
    this.model.save();    
    
  }
  }
});
