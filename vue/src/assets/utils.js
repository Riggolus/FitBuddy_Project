// utils.js

/**
 * Formats a Date object as yyyy-mm-dd
 * @param {Date} date - The date to format
 * @returns {string} - The formatted date
 */
export function formatDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are zero-based
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }
  
  /**
   * Formats a Date object as military time (HH:mm)
   * @param {Date} date - The date to format
   * @returns {string} - The formatted time
   */
  export function formatTime(date) {
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    return `${hours}:${minutes}`;
  }