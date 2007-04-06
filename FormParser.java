/**
 * @author The Blue Overdose Project
 * E-mail: blueover AT gmail com
 * Phlooder Website: http://code.google.com/p/phlooder
 * */

import au.id.jericho.lib.html.*;
import java.net.*;
import java.util.*;

/**
 * This class is responsible for finding and parsing the forms of an 
 * HTML document. It uses the Jericho HTML parser.
 * TODO: Default value isn't set!
 * */
class FormParser{
	private Source phisher;
	private FormFields fields;
	private String originalURI;
	FormParser(String uri){
		try{
			phisher=new Source(new URL(uri));
			originalURI=uri;
			fields=phisher.findFormFields();
		
		}catch(Exception e){
			System.out.println("Parser: Cannot read source!"+uri);
			return;
		}
		
	}
	public Form loadForm(){
		String formAction=new String();
		String formMethod=new String();
		
		List forms=phisher.findAllElements(Tag.FORM);
		if (forms.iterator().hasNext()){
			Element form=(Element)forms.iterator().next();
			formAction=form.getAttributes().getValue("action");
			formMethod=form.getAttributes().getValue("method");
		}
		System.out.println(formAction+"-"+formMethod);
		
		Form ret=new Form(formAction,formMethod);
		ret.setURI(originalURI);
		for(Iterator i=fields.iterator();i.hasNext();){
			
			au.id.jericho.lib.html.FormField f=(au.id.jericho.lib.html.FormField)i.next();
			FormControlType type=f.getFormControl().getFormControlType();
			String name=f.getName();
			System.out.println(type+" "+name);
			Collection values=f.getPredefinedValues();
			String value=new String();
			Flood floodType=new Flood();
			for(Iterator v=values.iterator();v.hasNext();){
				value=(String)v.next(); 
				System.out.println("- "+value);
				if (value.length()>0) floodType.type=Flood.NOP;
			}
			values=f.getValues();
			
			for(Iterator v=values.iterator();v.hasNext();){
				value=(String)v.next(); 
				System.out.println("- "+value);
				if (value.length()>0) floodType.type=Flood.NOP;
			}
			FormField ff=new FormField(type.toString(),value,name,floodType);
			ret.addField(ff);
		}
		return ret;
	}
}