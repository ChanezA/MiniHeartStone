import { Component, OnInit } from '@angular/core';
import {CARDS} from '../mock-my-hand';
import {MINION} from '../mock-my-board';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: [ './game.component.scss' ]
})
export class GameComponent implements OnInit {
  cardsHnd = CARDS;
  cardsInMyboard = MINION;
 // cardsInOpponantBorad = CARDS;


  constructor() { }

  ngOnInit() {

  }
}
