import { Component, OnInit } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import $ from 'jquery';
import {HEROES} from '../mock-heroes';
import { Hero } from '../heroes/hero';

import {CARDS} from '../mock-my-hand';
import { Card } from '../game/card';
import {MINION} from '../mock-my-board';
import { Minion } from '../game/minion';
import {MYHERO} from '../mock-my-heroInGame';
import {OPPHERO} from '../mock-opp-heroInGame';
import {MINIONOPP} from '../mock-opponant-board';
import { HeroInGame } from '../game/heroInGame';

import {stringify} from 'querystring';

export class WantPlayService {

  private serverUrl = 'http://localhost:8080/socket';
  private stompClient;
  private plSubscription;

  private myUserId = 'null';
  private myGameId = 'null';
  private myPseudo = 'null';

  constructor() {
  }

  initializeWebSocketConnection() {
    const ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    const that = this;
    this.stompClient.connect({}, function(frame) {
            that.stompClient.subscribe('/chat', (message) => {
              if (message.body) {
                let lel: 0;
                lel = JSON.parse(message.body).length;
                for (let i = 0; i < lel; i++) {
                  let hr: Hero;
                  hr = { name: JSON.parse(message.body)[i] , pseudo: 'pseudo', difficulty: 1};
                  HEROES[i] = hr;
                }
        }
      });
    });
    // this.playerSubscription = true;
    setTimeout(
      () => {
        this.sendMessage('waw');
      }, 500
    );
    this.plSubscription = this.stompClient;
  }

  sendIWantPlay ( numb, pseudo, hero) {
    if (this.plSubscription) {
      this.plSubscription.unsubscribe();
      console.log('JE FAIS UN TEST');
      this.myPseudo = pseudo;
      this.plSubscription = this.stompClient.subscribe('/chat/' + pseudo, (message) => {
        console.log('YYYYYYYYYYYYYYYYYYYYYYYYYYESSSSSSSSSSSS i am a player');
        // comment
        const msgStr = JSON.parse(message.body).rtrnmess;
        console.log(msgStr);
        const msgStrTab = msgStr.split('(!!)');
        if (msgStrTab[0] === 'game is ready') {
          this.myGameId = msgStrTab[1];
          console.log('this is my game Id : ' + this.myGameId);
        } else if (msgStrTab[0] === 'this is your hand') {
          console.log('LE MESSAGE : ' +  msgStrTab[1]);
          const allMyHandCards = msgStrTab[1].split('(CardDesc)');
          for (let i = 0; i < allMyHandCards.length; i++) {
            console.log('LES CARTES DE MA MAIN : ' +  allMyHandCards[i]);
            const cardAttribute = allMyHandCards[i].split('!!');
            let crd: Card;
            crd = { uuid: cardAttribute[0] , name: cardAttribute[1], description: cardAttribute[2], manaCost: cardAttribute[3]};
            console.log('-----------------------------------');
            console.log('ID : ' + cardAttribute[0] + ' name : ' + cardAttribute[1] + ' descri : ' + cardAttribute[2] +
              ' manaCOst : ' + cardAttribute[3]);
            CARDS[i] = crd;
          }
        } else if (msgStrTab[0] === 'this is your opponent board') {
          console.log('LE MESSAGE des cartes du board : ' +  msgStrTab[1]);
          const allMyHandCards = msgStrTab[1].split('(CardDesc)');
          for (let i = 0; i < allMyHandCards.length; i++) {
            console.log('LES CARTES DE MON BOARD : ' +  allMyHandCards[i]);
            const cardAttribute = allMyHandCards[i].split('!!');
            let min: Minion;
            min = { uuid: cardAttribute[0] , name: cardAttribute[1],
              description: cardAttribute[2], att: cardAttribute[3], life: cardAttribute[4]};
            console.log('@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@');
            console.log('ID : ' + cardAttribute[0] + ' name : ' + cardAttribute[1] + ' descri : ' + cardAttribute[2] +
              ' att : ' + cardAttribute[3], ' life : ' + cardAttribute[4]);
            MINIONOPP[i] = min;
          }
        } else if (msgStrTab[0] === 'this is your board') {
          console.log('LE MESSAGE des cartes du board : ' +  msgStrTab[1]);
          const allMyHandCards = msgStrTab[1].split('(CardDesc)');
          for (let i = 0; i < allMyHandCards.length; i++) {
            console.log('LES CARTES DE MON BOARD : ' +  allMyHandCards[i]);
            const cardAttribute = allMyHandCards[i].split('!!');
            let min: Minion;
            min = { uuid: cardAttribute[0] , name: cardAttribute[1],
              description: cardAttribute[2], att: cardAttribute[3], life: cardAttribute[4]};
            console.log('@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@');
            console.log('ID : ' + cardAttribute[0] + ' name : ' + cardAttribute[1] + ' descri : ' + cardAttribute[2] +
              ' att : ' + cardAttribute[3], ' life : ' + cardAttribute[4]);
            MINION[i] = min;
          }
        } else if (msgStrTab[0] === 'Info on your hero') {
          console.log('LE MESSAGE info de mon hero : ' +  msgStrTab[1]);
          MYHERO.uuid = msgStrTab[1];
          MYHERO.name = msgStrTab[2];
          MYHERO.mana = Number(msgStrTab[3]);
          MYHERO.life =  Number(msgStrTab[4]);
          MYHERO.armor =  Number(msgStrTab[5]);
          MYHERO.current = Number(msgStrTab[6]);
          console.log('my ' + Boolean(msgStrTab[6]));
        } else if (msgStrTab[0] === 'Info on your opponent') {
          console.log('LE MESSAGE info de mon opp : ' +  msgStrTab[1]);
          OPPHERO.uuid = msgStrTab[1];
          OPPHERO.name = msgStrTab[2];
          OPPHERO.mana = Number(msgStrTab[3]);
          OPPHERO.life =  Number(msgStrTab[4]);
          OPPHERO.armor =  Number(msgStrTab[5]);
          OPPHERO.current = Number(msgStrTab[6]);
          console.log('opp ' + Boolean(msgStrTab[6]));
        } else if (msgStrTab[0] === 'Current turn') {
          console.log('LE MESSAGE je passe mon tour : ' +  msgStrTab[1]);
          if (msgStrTab[1] === 'Vous etes le joueur courant') { MYHERO.current = 1; } else {MYHERO.current = 0 ; }
        } else {
          this.myUserId = msgStrTab[0];
          console.log(this.myUserId);
        }

      });
    }
    // let nmd: String = numb.toString();
    setTimeout(
      () => {
        this.stompClient.send('/app/send/iWantPlay' , {}, JSON.stringify({pseudo: pseudo, hero: hero, lvl: numb}));
      }, 500
    );
    console.log('----------------------------------------');
    console.log(pseudo + hero + numb);
    console.log('----------------------------------------');
  }

  sendMessage(message) {
    this.stompClient.send('/app/send/message' , {}, message);
  }

  sendMyHand() {
    this.stompClient.send('/app/send/myHand' , {}, this.myPseudo + '!!' + this.myGameId + '!!' + this.myUserId );
  }
  sendMyBoard() {
    this.stompClient.send('/app/send/myBoard' , {}, this.myPseudo + '!!' + this.myGameId + '!!' + this.myUserId );
  }
  sendMyOppBoard() {
    this.stompClient.send('/app/send/myOppBoard' , {}, this.myPseudo + '!!' + this.myGameId + '!!' + this.myUserId );
  }
  sendPassMyTurn() {
    this.stompClient.send('/app/send/passTurn' , {}, this.myPseudo + '!!' + this.myGameId + '!!' + this.myUserId );
  }
  sendInvock(message) {
    this.stompClient.send('/app/send/invock' , {}, this.myPseudo + '!!' + this.myGameId + '!!' + this.myUserId + '!!' + message );
  }
  sendMyInfo() {
    this.stompClient.send('/app/send/MyHeroInfo' , {}, this.myPseudo + '!!' + this.myGameId + '!!' + this.myUserId );
    this.stompClient.send('/app/send/MyOppInfo' , {}, this.myPseudo + '!!' + this.myGameId + '!!' + this.myUserId );
  }

  sendIAttack(message1, message2) {
    this.stompClient.send('/app/send/MyHeroInfo' , {}, this.myPseudo + '!!' +
      this.myGameId + '!!' + this.myUserId + '!!' + message1 + '!!' + message2 );
  }
}
