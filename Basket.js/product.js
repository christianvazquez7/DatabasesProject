/**
 * New node file
 */
module.exports =  { 
	product : function (name, ISBN,Manufacturer,width,height,depth,description,features)
	{
		
		this.name = name;
		this.ISBN = ISBN;
		this.Manufacturer =  Manufacturer;
		this.width=width;
		this.height=height;
		this.depth=depth;
		this.description=description;
		this.features=features;
		
		
	}
};
