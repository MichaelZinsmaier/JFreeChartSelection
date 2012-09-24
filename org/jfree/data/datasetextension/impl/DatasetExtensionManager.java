package org.jfree.data.datasetextension.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jfree.data.datasetextension.DatasetExtension;
import org.jfree.data.general.Dataset;

public class DatasetExtensionManager {

	private HashMap registeredExtensions = new HashMap(); 
	
	public void registerDatasetExtension(DatasetExtension extension) {
		List extensionList = (List) registeredExtensions.get(extension.getDataset()); 
		if (extensionList != null) {
			extensionList.add(extension);
		} else {
			extensionList = new LinkedList();
			extensionList.add(extension);
			registeredExtensions.put(extension.getDataset(), extensionList);
		}
	}

	
	public boolean supports(Dataset dataset, Class interfaceClass) {
		return getExtension(dataset, interfaceClass) != null;
	}
	
	
	public Object getExtension(Dataset dataset, Class interfaceClass) {		
		if (interfaceClass.isAssignableFrom(dataset.getClass())) {
			//the dataset supports the interface
			return dataset;
		} else {
			List extensionList = (List) registeredExtensions.get(dataset);
			if (extensionList != null) {
				Iterator iter = extensionList.iterator();
				Object extension;
				while (iter.hasNext()) {
					extension = iter.next();
					if (interfaceClass.isAssignableFrom(extension.getClass())) {
						//the dataset does not support the extension but
						//a matching helper object is registered for the dataset
						return extension;
					}
				}
			}
		}
				
		return null;		
	}

	
}
