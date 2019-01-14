import { Component, OnInit } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import $ from 'jquery';
import {HEROES} from './mock-heroes';
import { Hero } from './heroes/hero';
import {forEach} from '@angular/router/src/utils/collection';
import {WantPlayService} from './services/wantPlay.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  private serverUrl = 'http://localhost:8080/socket';
  private title = 'WebSockets chat';
  private stompClient;

  constructor(private wantPlayService: WantPlayService) {
    this.wantPlayService.initializeWebSocketConnection();
  }

  ngOnInit() {

  }

  sendMessage(message) {
    this.wantPlayService.sendMessage(message);
  }
}
