import { Component, OnInit } from '@angular/core';
import { Hero } from '../heroes/hero';
import { HEROES } from '../mock-heroes';
import { AppComponent } from '../app.component';
import {WantPlayService} from '../services/wantPlay.service';

@Component({
  selector: 'app-heroes',
  templateUrl: './heroes.component.html',
  styleUrls: ['./heroes.component.scss'],
})

export class HeroesComponent implements OnInit {

  heroes = HEROES;
  selectedHero: Hero;

  constructor(private wantPlayService: WantPlayService) { }

  ngOnInit() {
  }

  onSelect(hero: Hero): void {
    this.selectedHero = hero;
  }

  iWantPlay(): void {
   this.wantPlayService.sendIWantPlay(this.selectedHero.difficulty, this.selectedHero.pseudo, this.selectedHero.name);
  }
}
