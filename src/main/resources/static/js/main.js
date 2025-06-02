const api = axios.create({ baseURL: 'http://localhost:8080/api' });
const categories = [
  "DESKTOP", "MONITOR", "LAPTOP", "TABLET", "PRINTER",
  "MEMORY_DEVICE", "PROCESSOR", "POWER_SUPPLY", "INPUT_DEVICE"
];
function goBack() {
  document.getElementById('content').innerHTML = '';
}
