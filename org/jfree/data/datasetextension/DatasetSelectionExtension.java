package org.jfree.data.datasetextension;

public interface DatasetSelectionExtension extends DatasetExtension {
	
	public boolean isSelected(DatasetCursor cursor);
	
	public void setSelected(DatasetCursor cursor, boolean selected);
	
	public void clearSelection();
	
}
