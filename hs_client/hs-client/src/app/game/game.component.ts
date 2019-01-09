import { Component, OnInit } from '@angular/core';
import {CARDS} from '../mock-my-hand';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: [ './game.component.scss' ]
})
export class GameComponent implements OnInit {
  cardsHnd = CARDS;
  //cardsInMyboard = CARDS;
 // cardsInOpponantBorad = CARDS;


  constructor() { }

  ngOnInit() {

  }
}
