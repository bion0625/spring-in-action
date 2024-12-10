package app.reacter;

public class Player {
	private String firstName;
	private String secondName;

	public Player(String firstName, String secondName) {
		this.firstName = firstName;
		this.secondName = secondName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	@Override
	public String toString() {
		return this.firstName + this.secondName;
	}

	@Override
	public boolean equals(Object obj) {
		Player player = (Player) obj;
		return player.getFirstName().equals(this.firstName) && player.getSecondName().equals(this.secondName);
	}
}
