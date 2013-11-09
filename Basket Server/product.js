/**
 * New node file
 */
module.exports =  { 
	product : function (name, productPId,manufacturer,width,height,depth,dimensions)
	{
		
		this.name = name;
		this.productPId = productPId;
		this.manufacturer =  manufacturer;
		this.width=width;
		this.height=height;
		this.depth=depth;
		this.dimensions=dimensions;
		
	}
};
