const api = axios.create({ baseURL: 'https://computerstore-cci2.onrender.com/api' });
const categories = [
  "DESKTOP", "MONITOR", "LAPTOP", "TABLET", "PRINTER",
  "MEMORY_DEVICE", "PROCESSOR", "POWER_SUPPLY", "INPUT_DEVICE"
];
function goBack() {
  document.getElementById('content').innerHTML = '';
}
