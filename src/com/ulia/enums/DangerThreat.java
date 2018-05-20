package com.ulia.enums;

// Перечисление опасности угроз
public enum DangerThreat {

	EMPTY("", 0), // если не выбрано

	HIGH("высокая опасность", 2), // высокая
	
	MEDIUM("средняя опасность", 1), // средняя

	LOW("низкая опасность", 0); // низкая

	private String name; // наименование опасности  угрозы
	private int mask; // маска в виде номера

	// Конструктор
	DangerThreat(String name, int mask) {
		this.name = name;
		this.mask = mask;

	}

	// Геттер для наименования опасности угрозы
	public String getName() {
		return name;
	}

	// Геттер для маски
	public int getMask() {
		return mask;
	}
}
