<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='utf-8' />
<meta name="viewport"
    content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link
    href='https://cdn.jsdelivr.net/npm/fullcalendar@5.8.0/main.min.css'
    rel='stylesheet' />
<script
    src='https://cdn.jsdelivr.net/npm/fullcalendar@5.8.0/main.min.js'></script>
<script
    src='https://cdn.jsdelivr.net/npm/fullcalendar@5.8.0/locales-all.min.js'></script>
<style>
/* FullCalendar CSS 및 기타 스타일 */
body {
    font-family: 'Arial', 'Helvetica Neue', 'Helvetica', sans-serif;
    padding: 20px;
}

#wrap {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
}

#external-events {
    width: 200px;
    margin-right: 20px;
}

#calendar-wrap {
    flex-grow: 1;
}

.modal {
    display: none;
    position: fixed;
    z-index: 1;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    overflow: auto;
    background-color: rgba(0, 0, 0, 0.4);
}

.modal-content {
    background-color: #fefefe;
    margin: 10% auto;
    padding: 20px;
    border: 1px solid #888;
    width: 80%;
}

.close {
    color: #aaa;
    float: right;
    font-size: 28px;
    font-weight: bold;
}

.close:hover,
.close:focus {
    color: black;
    text-decoration: none;
    cursor: pointer;
}

/* 라벨 색상 */

.color-select {
    display: flex;
    flex-wrap: wrap;
}

.color-option {
    display: flex;
    align-items: center;
    margin-right: 20px;
    margin-bottom: 10px;
    cursor: pointer;
}

.color-box {
    width: 20px;
    height: 20px;
    display: inline-block;
    border: 1px solid #ccc;
    margin-right: 5px;
    vertical-align: middle;
}


</style>
</head>
<body>
    <div id='wrap'>
	    <!-- 일정 추가 버튼 -->
	    <div>
		    <button id="addScheduleButton">일정 추가</button>
	    </div>
        <!-- 외부 일정 목록 -->
        <div id='external-events'>
            <h4>드래그 가능한 일정</h4>
            <div id='external-events-list'>
                <!-- 드래그 가능한 일정 항목 -->
                <div class='fc-event' data-event='{"title":"일정 1", "color":"red"}'>일정 1</div>
                <div class='fc-event' data-event='{"title":"일정 2", "color":"blue"}'>일정 2</div>
                <div class='fc-event' data-event='{"title":"일정 3", "color":"green"}'>일정 3</div>
            </div>
        </div>
        <!-- 캘린더 영역 -->
        <div id='calendar-wrap'>
            <div id='calendar'></div>
        </div>
    </div>

    <!-- 일정 추가 모달 -->
    <div id="addScheduleModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <h2>일정 추가</h2>
            <form id="scheduleForm">
                <label for="scheduleTitle">일정 제목:</label><br>
                <input type="text" id="scheduleTitle" name="scheduleTitle" required><br><br>
                <label for="scheduleDescription">일정 내용:</label><br>
                <textarea id="scheduleDescription" name="scheduleDescription"></textarea><br><br> <label for="scheduleColor">일정 라벨:</label>
				<div class="color-select">
					<input type="radio" id="colorRed" name="scheduleColor" value="1">
					<label for="colorRed" class="color-option"> <span
						class="color-box" style="background-color: red;"></span> 
					</label> <input type="radio" id="colorOrange" name="scheduleColor"
						value="2"> <label for="colorOrange" class="color-option">
						<span class="color-box" style="background-color: orange;"></span>
						
					</label> <input type="radio" id="colorYellow" name="scheduleColor"
						value="3"> <label for="colorYellow" class="color-option">
						<span class="color-box" style="background-color: yellow;"></span>
						
					</label> <input type="radio" id="colorGreen" name="scheduleColor"
						value="4"> <label for="colorGreen" class="color-option">
						<span class="color-box" style="background-color: green;"></span> 
					</label> <input type="radio" id="colorBlue" name="scheduleColor"
						value="5"> <label for="colorBlue" class="color-option"> <span
						class="color-box" style="background-color: blue;"></span> 
					</label> <input type="radio" id="colorPurple" name="scheduleColor"
						value="7"> <label for="colorPurple" class="color-option">
						<span class="color-box" style="background-color: purple;"></span>
						
					</label>

				</div>
				<br> <br> <label for="scheduleLocation">일정 장소:</label><br>
                <input type="text" id="scheduleLocation" name="scheduleLocation"><br><br>
                <label for="scheduleCategory">일정 종류:</label>
                <select id="scheduleCategory" name="scheduleCategory">
                    <option value="my">내 캘린더</option>
                    <option value="share">공유 캘린더</option>
                </select><br><br>
                <label for="scheduleStart">일정 시작일:</label><br>
                <input type="datetime-local" id="scheduleStart" name="scheduleStart" required><br><br>
                <label for="scheduleEnd">일정 종료일:</label><br>
                <input type="datetime-local" id="scheduleEnd" name="scheduleEnd" required><br><br>
                <input type="submit" value="일정 추가">
            </form>
        </div>
    </div>

    

    <script>
        $(document).ready(function() {
            // FullCalendar 생성
            var calendarEl = document.getElementById('calendar');
            var calendar = new FullCalendar.Calendar(calendarEl, {
                initialView: 'dayGridMonth',
                locale: 'ko',
                editable: true,
                droppable: true,
                eventReceive: function(info) {
                    // 드래그 앤 드롭으로 일정 추가
                    var event = JSON.parse(info.draggedEl.getAttribute('data-event'));
                    calendar.addEvent({
                        title: event.title,
                        start: info.event.startStr,
                        end: info.event.endStr,
                        backgroundColor: event.color
                    });
                },
                eventClick: function(info) {
                    // 클릭하여 일정 상세 보기
                    alert('일정 제목: ' + info.event.title);
                }
            });
            calendar.render();

            // 모달 관리
            var modal = document.getElementById('addScheduleModal');
            var span = document.getElementsByClassName('close')[0];
            var addButton = document.getElementById('addScheduleButton');

            // 모달 열기
            addButton.onclick = function() {
                modal.style.display = "block";
            }

            // 모달 닫기
            span.onclick = function() {
                modal.style.display = "none";
            }

            // Submit 이벤트 처리
            $('#scheduleForm').submit(function(e) {
                e.preventDefault();

                // 폼 데이터 가져오기
                var scheduleData = {
                    scheduleTitle: $('#scheduleTitle').val(),
                    scheduleContent: $('#scheduleDescription').val(),
                    scheduleLabel: $('#scheduleColor').val(),
                    schedulePlace: $('#scheduleLocation').val(),
                    scheduleType: $('#scheduleCategory').val(),
                    scheduleStartDate: $('#scheduleStart').val(),
                    scheduleEndDate: $('#scheduleEnd').val()
                };

                // 서버로 일정 데이터 전송
                $.ajax({
                    type: 'POST',
                    url: '/schedule/insertSchedule',
                    contentType: 'application/json',
                    data: JSON.stringify(scheduleData),
                    success: function(response) {
                        alert('일정 추가 완료');
                        modal.style.display = "none";
                        $('#scheduleForm')[0].reset();
                        // 필요에 따라 캘린더 업데이트 또는 리다이렉트
                    },
                    error: function(xhr, status, error) {
                        alert('일정 추가 실패: ' + error);
                        // 필요에 따라 오류 처리
                    }
                });
            });
        });
    </script>
</body>
</html>
