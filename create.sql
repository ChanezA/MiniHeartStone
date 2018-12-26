create table Minion (
    name VARCHAR(40),
    description VARCHAR(200),
    manaCost INTEGER NOT NULL,
    attack INTEGER NOT NULL,
    life INTEGER NOT NULL,
    hasTaunt BOOLEAN NOT NULL,
    hasLifeSteal BOOLEAN NOT NULL,
    hasCharge BOOLEAN NOT NULL, 
    PRIMARY KEY (name)
)