document.addEventListener('DOMContentLoaded', () => {
    const meetingList = document.getElementById('meeting-list');
    const errorMessage = document.getElementById('error-message');
    const updateForm = document.getElementById('update-form'); // Assuming you have an update form in your HTML

    async function fetchMeetings() {
        const params = new URLSearchParams(window.location.search); // Get URL parameters
        let endpoint = 'http://localhost:8080/api/v1/meetings/'; // Base endpoint
    
        // Check for various parameters to determine the endpoint
        if (params.has('id')) {
            const id = params.get('id');
            endpoint += `findById?id=${encodeURIComponent(id)}`; 
        } else if (params.has('type')) {
            const type = params.get('type');
            endpoint += `findByType?type=${encodeURIComponent(type)}`; 
        } else if (params.has('organizer')) {
            const organizer = params.get('organizer');
            endpoint += `findByOrganizer?organizer=${encodeURIComponent(organizer)}`; 
        } else if (params.has('recurrence')) {
            const recurrence = params.get('recurrence');
            endpoint += `findByRecurrence?recurrence=${encodeURIComponent(recurrence)}`; 
        } else if (params.has('status')) {
            const status = params.get('status');
            endpoint += `findByStatus?status=${encodeURIComponent(status)}`; 
        } else {
            endpoint += 'get_all'; // Default case for fetching all meetings
        }
    
        try {
            const response = await fetch(endpoint);
            if (!response.ok) {
                throw new Error('Failed to load meetings.');
            }
            const meetings = await response.json();
            displayMeetings(meetings);
        } catch (error) {
            errorMessage.textContent = error.message;
            console.error('Fetch error:', error); // Log the error for debugging
        }
    }
    
    const displayMeetings = (meetings) => {
        meetingList.innerHTML = ''; // Clear existing list
        if (Array.isArray(meetings)) {
            meetings.forEach(meeting => {
                const listItem = document.createElement('li');
                listItem.innerHTML = `
                    <strong>${meeting.description}</strong> (Organizer: ${meeting.organizer.name})<br>
                    Start: ${new Date(meeting.startTime).toLocaleString()} - End: ${new Date(meeting.endTime).toLocaleString()}<br>
                    Type: ${meeting.type} | Status: ${meeting.status || 'N/A'}
                    <button onclick="deleteMeeting(${meeting.mid})">Delete</button>
                    <button onclick="showUpdateMeetingForm(${meeting.mid})">Update</button>
                `;
                meetingList.appendChild(listItem);
            });
        } else {
            // If not an array, handle as a single meeting object
            const listItem = document.createElement('li');
            listItem.innerHTML = `
                <strong>${meetings.description}</strong> (Organizer: ${meetings.organizer.name})<br>
                Start: ${new Date(meetings.startTime).toLocaleString()} - End: ${new Date(meetings.endTime).toLocaleString()}<br>
                Type: ${meetings.type} | Status: ${meetings.status || 'N/A'}
                <button onclick="deleteMeeting(${meetings.mid})">Delete</button>
                <button onclick="showUpdateMeetingForm(${meetings.mid})">Update</button>
            `;
            meetingList.appendChild(listItem);
        }
    };

    window.deleteMeeting = async (mid) => {
        try {
            const response = await fetch(`http://localhost:8080/api/v1/meetings/delete/${mid}`, {
                method: 'DELETE'
            });
            if (!response.ok) {
                throw new Error('Failed to delete the meeting.');
            }
            fetchMeetings(); // Refresh the list after deletion
        } catch (error) {
            errorMessage.textContent = error.message;
            console.error('Delete error:', error);
        }
    };

    window.showUpdateMeetingForm = (mid) => {
        // Functionality to show the update form
        updateForm.style.display = 'block';
        // Load existing meeting data into the form (not implemented here)
    };

    // Fetch meetings when the page loads
    fetchMeetings();
});
