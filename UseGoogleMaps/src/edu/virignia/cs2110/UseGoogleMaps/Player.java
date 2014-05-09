package edu.virignia.cs2110.UseGoogleMaps;

public class Player {
	private boolean hasBomb;
	private int bombs;
	private int health;
	private boolean dead;

	
	public int getHealth() {
		return health;
	}
	public int takeDamage() {
		this.health = this.health - 1;
		return this.health;
		
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public boolean isHasBomb() {
		return hasBomb;
	}

	public void setHasBomb(boolean hasBomb) {
		this.hasBomb = hasBomb;
	}
	
	public Player(){
		this.hasBomb = true;
		this.health = 3;
		this.dead = false;
		this.bombs = 5;
	}
	
	public void takeIt() {
		this.health--;
		if(this.health == 0) {
			this.dead = true;
		}
	}
	public void findFood() {
		if(this.health <= 3) {
		this.health++;
		}
	}
	public int getBombs() {
		return bombs;
	}
	public void setBombs(int bombs) {
		this.bombs = bombs;
	}
	public void findBombs() {
		if(this.bombs < 5) {
		this.health++;
		}
	}
	

	
}
