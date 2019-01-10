create table minion (
    name VARCHAR(40),
    description VARCHAR(200),
    mana_cost INTEGER NOT NULL,
    attack INTEGER NOT NULL,
    life INTEGER NOT NULL,
    has_taunt BOOLEAN NOT NULL,
    has_life_steal BOOLEAN NOT NULL,
    has_charge BOOLEAN NOT NULL,
    hero_name VARCHAR(40),
    picture_url VARCHAR(100),
    PRIMARY KEY (name)
);

create table spell (
    name VARCHAR(40),
    description VARCHAR(200),
    mana_cost INTEGER NOT NULL,
    hero_name VARCHAR(40),
    picture_url VARCHAR(100),
    PRIMARY KEY (name)
);
