package org.gamenet.application.mm8leveleditor.data.mm6.fileFormat;


public class ItemsTxt extends Txt {

	public class Item
	{
		private String picFile = null;
		private String name = null;
		private String equipStat = null;
		private String skillGroup = null;
		private String mod1 = null;
		private String mod2 = null;
		private String material = null;
		private String id_rep_st = null;
		private String notIdentifiedName = null;
		private String spriteIndex = null;
		private String shape = null;
		private String equipX = null;
		private String equipY = null;
		private String notes = null;
		
		public String getPicFile() {
			return picFile;
		}
		public void setPicFile(String picFile) {
			this.picFile = picFile;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getEquipStat() {
			return equipStat;
		}
		public void setEquipStat(String equipStat) {
			this.equipStat = equipStat;
		}
		public String getSkillGroup() {
			return skillGroup;
		}
		public void setSkillGroup(String skillGroup) {
			this.skillGroup = skillGroup;
		}
		public String getMod1() {
			return mod1;
		}
		public void setMod1(String mod1) {
			this.mod1 = mod1;
		}
		public String getMod2() {
			return mod2;
		}
		public void setMod2(String mod2) {
			this.mod2 = mod2;
		}
		public String getMaterial() {
			return material;
		}
		public void setMaterial(String material) {
			this.material = material;
		}
		public String getId_rep_st() {
			return id_rep_st;
		}
		public void setId_rep_st(String id_rep_st) {
			this.id_rep_st = id_rep_st;
		}
		public String getNotIdentifiedName() {
			return notIdentifiedName;
		}
		public void setNotIdentifiedName(String notIdentifiedName) {
			this.notIdentifiedName = notIdentifiedName;
		}
		public String getSpriteIndex() {
			return spriteIndex;
		}
		public void setSpriteIndex(String spriteIndex) {
			this.spriteIndex = spriteIndex;
		}
		public String getShape() {
			return shape;
		}
		public void setShape(String shape) {
			this.shape = shape;
		}
		public String getEquipX() {
			return equipX;
		}
		public void setEquipX(String equipX) {
			this.equipX = equipX;
		}
		public String getEquipY() {
			return equipY;
		}
		public void setEquipY(String equipY) {
			this.equipY = equipY;
		}
		public String getNotes() {
			return notes;
		}
		public void setNotes(String notes) {
			this.notes = notes;
		}
	}
	
	public String getItemNameAtIndex(int index)
	{
		return getStringForMatchingColumnValue(2, 0, String.valueOf(index));
	}
}
