import { Component, OnInit } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import $ from 'jquery';
import {HEROES} from '../mock-heroes';
import { Hero } from '../heroes/hero';
import {stringify} from 'querystring';

export class WantPlayService {

  private serverUrl = 'http://localhost:8080/socket';
  private stompClient;
  private plSubscription;
  // private playerSubscription = false;

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
      this.plSubscription = this.stompClient.subscribe('/chat/' + pseudo, (message) => {
        console.log('YYYYYYYYYYYYYYYYYYYYYYYYYYESSSSSSSSSSSS');
        let pseudo = JSON.parse(message.body).pseudo;
        console.log(pseudo);
      });
    }
    setTimeout(
      () => {
        this.stompClient.send('/app/send/iWantPlay' , {}, JSON.stringify({pseudo: pseudo, hero: hero, numb: numb}));
      }, 500
    );
    console.log('----------------------------------------');
    console.log(pseudo + hero + numb);
    console.log('----------------------------------------');
  }

  sendMessage(message) {
    this.stompClient.send('/app/send/message' , {}, message);
  }
}
