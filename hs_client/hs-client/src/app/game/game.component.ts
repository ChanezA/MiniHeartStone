import { Component, OnInit } from '@angular/core';
import {CARDS} from '../mock-my-hand';
import {MINION} from '../mock-my-board';
import {MINIONOPP} from '../mock-opponant-board';
import {WantPlayService} from '../services/wantPlay.service';
import {MYHERO} from '../mock-my-heroInGame';
import {OPPHERO} from '../mock-opp-heroInGame';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: [ './game.component.scss' ]
})
export class GameComponent implements OnInit {
  cardsHnd = CARDS;
  cardsInMyboard = MINION;
  cardsInOpponantBorad = MINIONOPP;
  myhrInfo = MYHERO;
  myOppInfo = OPPHERO;

  UUIDselectedCard;
  UUIDMyselectedMinion;
  UUIDoppMinionOrHero;


  constructor(private wantPlayService: WantPlayService) {}

  ngOnInit() {

  }

  sendMyHand(): void {
    this.wantPlayService.sendMyHand();
  }
  sendMyBoard(): void {
    this.wantPlayService.sendMyBoard();
  }
  passMyTurn(): void {
  this.wantPlayService.sendPassMyTurn();
  }
  invock(): void {
    this.wantPlayService.sendInvock(this.UUIDselectedCard);
  }
  sendMyoppBoard(): void {
    this.wantPlayService.sendMyOppBoard();
  }
  sendMyInfo(): void {
    this.wantPlayService.sendMyInfo();
  }
  sendAtatck(): void {
    this.wantPlayService.sendIAttack(this.UUIDMyselectedMinion, this.UUIDoppMinionOrHero);
  }




  oui(message) {
    this.UUIDselectedCard = message;
    console.log(this.UUIDselectedCard);
  }
  selectMyMin(message) {
    this.UUIDMyselectedMinion = message;
    console.log(this.UUIDMyselectedMinion);
  }
  selectOppMin(message) {
    this.UUIDoppMinionOrHero = message;
    console.log(this.UUIDoppMinionOrHero);
  }
}
