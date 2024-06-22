/**
 * 채팅서버 기능
 */

//ws : http
//wss : https

// WebSocket 서버에 연결
const server = new WebSocket("ws://localhost:9090/spring/chatting")

// 서버와 연결이 열렸을 때 실행되는 함수
server.onopen = (response) => {
    // 새로운 메시지 객체 생성 (type="open", sender=loginId)
    const msg = new Message("open", loginId);
    console.log(msg);
    // 서버로 메시지를 전송
    server.send(msg.convert()); // 서버로 보낼 데이터를 JSON 문자열로 변환하여 전송
}

// 서버로부터 메시지를 받을 때 실행되는 함수
server.onmessage = response => {
    // 서버에서 온 데이터를 Message 객체로 변환
    const receiveMsg = Message.deconvert(response.data);
    // 메시지 타입에 따라 분기 처리
    switch (receiveMsg.type) {
        case "open": alertMessage(receiveMsg); break; // 새로운 사용자가 접속했을 때
        case "msg": messagePrint(receiveMsg); break; // 메시지를 받았을 때
        case "attend": addAttend(receiveMsg); break; // 참가자를 추가했을 때
        case "close": alertMessage(receiveMsg); break; // 사용자가 나갔을 때
    }
}

// 새로운 참가자를 추가하는 함수 (현재 구현되지 않음)
const addAttend = (msg) => {
	const clients = JSON.parse(msg.msg); // List형식으로 parsing // 원본값 : ["1234","admin"] 파싱 후 : Array(2)
	console.log(clients);
	const $attendContainer = document.querySelector("#attendContainer");
	$attendContainer.innerHTML=""; //비우기 (접속할 때 마다)
	const $ul =document.createElement("ul");
	$ul.classList.add("listcontainer");
	
	clients.map(e=>{ //리턴되는 값으로 
		const $li = document.createElement("li");
		$li.innerText = e;
		$li.classList.add("listfont");
		return $li;
	}).forEach(e => {
		$ul.appendChild(e);
	});
	$attendContainer.appendChild($ul);
}

// 메시지를 출력하는 함수
const messagePrint = (msg) => {
    console.log(msg);
    const $div = document.createElement("div");
    const $profile = document.createElement("img");
    $profile.setAttribute("src", `${path}/resources/images/yoo.png`);
    $profile.style.width="50px";
    $profile.style.height="50px";
    $profile.style.borderRadius="100px";
    $div.appendChild($profile);
    
    //보낸사람 태그 생성
    const $sender = document.createElement("sup");
    $sender.innerText = msg.sender;
    
    //메세지 출력 태그
    const $content = document.createElement("span");
    $content.innerText = msg.msg;
    
    $div.appendChild($sender);
    $div.appendChild($content);
    
    //메세지 컨테이너 디자인
    $div.classList.add("msgcontainer");
    if(msg.sender == loginId){
		$div.classList.add("right");
	}else{
		$div.classList.add("left");
	}
    document.querySelector("#chattingcontent").appendChild($div);
}

// 메시지를 서버로 보내는 함수
const sendMessage = () => {
    const inputData = document.querySelector("#msg").value; // 입력 필드의 값을 가져옴
    if (inputData.length > 0) {
        // 새로운 메시지 객체 생성 (type="msg", sender=loginId, msg=inputData)
        const msgObj = new Message("msg", loginId, "", "", inputData).convert();
        // 서버로 메시지를 전송
        server.send(msgObj);
    } else {
        alertMessage("메세지를 입력하세요"); // 메시지가 비어 있을 때 경고 메시지 출력
        document.querySelector("#msg").focus(); // 입력 필드에 포커스를 맞춤
    }
}

const alertMessage = (msg) => {
    // 경고 메시지 출력 로직을 여기에 작성
    const $container = $("<div>").addClass("alertContainer");
    const status = msg.type == "open" ? "접속" : "퇴장";
    const $content = $("<h4>").text(`${msg.sender}님이 ${status}하셨습니다.`); 
    $container.append($content);
    $('#chattingcontent').append($container);
    
}

// Message 클래스 정의
class Message {
    constructor(type = "", sender = "", receiver = "", room = "", msg = "") { // 기본값 설정
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.room = room;
        this.msg = msg;
    }
    
    // 객체를 JSON 문자열로 변환하는 메소드
    convert() {
        return JSON.stringify(this);
    }
    
    // JSON 문자열을 객체로 변환하는 정적 메소드
    static deconvert(data) {
        return JSON.parse(data);
    }
}
